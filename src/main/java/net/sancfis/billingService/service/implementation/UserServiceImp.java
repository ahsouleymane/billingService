package net.sancfis.billingService.service.implementation;

import lombok.RequiredArgsConstructor;
import net.sancfis.billingService.domain.User;
import net.sancfis.billingService.dto.UserDTO;
import net.sancfis.billingService.dtomapper.UserDTOMapper;
import net.sancfis.billingService.repository.UserRepository;
import net.sancfis.billingService.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
