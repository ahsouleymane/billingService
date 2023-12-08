package net.sancfis.billingService.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sancfis.billingService.domain.User;
import net.sancfis.billingService.exception.ApiException;
import net.sancfis.billingService.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static net.sancfis.billingService.query.UserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImp implements UserRepository<User> {

    public final NamedParameterJdbcTemplate jdbc;

    @Override
    public User create(User user) {
        // Vérifiez que l'e-mail est unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email déja utilisé !!! Réessayer.");
        // Enregistrer un nouvel utilisateur
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
        }catch (EmptyResultDataAccessException exception) {

        } catch (Exception exception) {

        }
        // Attribuer un role à l'utilisateur
        // Envoyer l'URL de vérification
        // Enregistrer l'URL dans la table de verification
        // Envoyer un email à l'utilisateur avec l'URL de verification
        // renvoyer l'utilisateur nouvellement créé
        // S'il y a des erreurs, lancez une exception avec le message approprié
        return null;
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
        return jdbc.queryForObject(CONST_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
    }
}
