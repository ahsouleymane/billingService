package net.sancfis.billingService.service;

import net.sancfis.billingService.domain.User;
import net.sancfis.billingService.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
