package net.sancfis.billingService.repository;

import net.sancfis.billingService.domain.Role;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    /* Operations basiques de CRUD */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    /* Autre operations plus complexe */
    void addRoleToUser(Long UserId, String roleName);

}
