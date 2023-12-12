package net.sancfis.billingService.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sancfis.billingService.domain.Role;
import net.sancfis.billingService.exception.ApiException;
import net.sancfis.billingService.repository.RoleRepository;
import net.sancfis.billingService.rowmapper.RoleRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static net.sancfis.billingService.enumeration.RoleType.ROLE_USER;
import static net.sancfis.billingService.query.RoleQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImp implements RoleRepository<Role> {

    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public Role create(Role data) {
            return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Ajout d'un rôle {} à l'identifiant de l'utilisateur {}", roleName, userId);
        try{
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("roleName",roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId", userId, "roleId", requireNonNull(role).getId()));

        }catch (
                EmptyResultDataAccessException exception) {
            throw new ApiException("Aucun rôle trouvé pour le nom: " + ROLE_USER.name());
        } catch (Exception exception) {
            throw new ApiException("Une erreur est survenue ! Réessayer.");
        }
    }

    @Override
    public Role getRoleByUserId(Long UserId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }
}
