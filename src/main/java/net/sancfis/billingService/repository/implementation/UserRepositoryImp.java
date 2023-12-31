package net.sancfis.billingService.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sancfis.billingService.domain.Role;
import net.sancfis.billingService.domain.User;
import net.sancfis.billingService.exception.ApiException;
import net.sancfis.billingService.repository.RoleRepository;
import net.sancfis.billingService.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;

import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static net.sancfis.billingService.enumeration.RoleType.*;
import static net.sancfis.billingService.enumeration.VerificationType.ACCOUNT;
import static net.sancfis.billingService.query.UserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImp implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // Vérifiez que l'e-mail est unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email déja utilisé ! Réessayer.");
        // Enregistrer un nouvel utilisateur
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            // Attribuer un role à l'utilisateur
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            // Envoyer l'URL de vérification
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            // Enregistrer l'URL dans la table de verification
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));
            // Envoyer un email à l'utilisateur avec l'URL de verification
            //emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            // renvoyer l'utilisateur nouvellement créé
            return user;
            // S'il y a des erreurs, lancez une exception avec le message approprié
        /*}catch (EmptyResultDataAccessException exception) {
            throw new ApiException("Aucun rôle trouvé pour le nom: " + ROLE_USER.name());*/
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Une erreur est survenue ! Réessayer.");
        }

    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(CONST_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }


}
