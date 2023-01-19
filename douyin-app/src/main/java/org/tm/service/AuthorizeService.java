package org.tm.service;

import org.springframework.stereotype.Service;
import org.tm.exception.UserExistsException;

import org.tm.pojo.User;
import org.tm.repository.UserRepository;


@Service
public class AuthorizeService {

    private final UserRepository userRepository;

    public AuthorizeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user){
        String username = user.getUsername();


        User userInDB = userRepository.selectUserBy(username);
        if(userInDB != null){
            throw new UserExistsException("用户名"+username+"已存在");
        }
        userRepository.insertUser(user);
        user.setUserId(user.getUserId());
    }

    public void login(User user){
        String username = user.getUsername();
        User userInDB = userRepository.selectUserBy(username);


        if(userInDB.getPassword().equals(user.getPassword())){
            user.setUserId(userInDB.getUserId());
        }
    }
}
