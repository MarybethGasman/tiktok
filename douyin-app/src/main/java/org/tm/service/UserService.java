package org.tm.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.tm.dto.UserDTO;
import org.tm.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value="user-info", key = "#userId")
    public UserDTO getUserInfoById(Long userId) {
        return userRepository.selectUserById(userId);
    }

}
