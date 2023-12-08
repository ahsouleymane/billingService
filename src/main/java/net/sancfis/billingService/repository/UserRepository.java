package net.sancfis.billingService.repository;

import net.sancfis.billingService.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User> {
    /* Operations basiques de CRUD */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    /* Autre operations plus complexe */
}
