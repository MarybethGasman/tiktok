package org.tm.service;

import org.springframework.stereotype.Service;
import org.tm.dto.Response;
import org.tm.dto.UserDTO;
import org.tm.dto.UserLoginResponse;
import org.tm.exception.UserExistsException;

import org.tm.po.UserPO;
import org.tm.repository.UserRepository;
import org.tm.util.JwtUtil;

import java.util.Optional;


@Service
public class AuthorizeService {

    private final UserRepository userRepository;

    public AuthorizeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserDTO userDto){
        String username = userDto.getUsername();

        UserPO userPO = new UserPO(
                username,
                userDto.getPassword()
        );

        UserDTO userInDB = userRepository.selectUserBy(username);
        if(userInDB != null){
            throw new UserExistsException("用户名"+username+"已存在");
        }
        userRepository.insertUser(userPO);
        userDto.setUserId(userPO.getUserId());
    }

    public UserLoginResponse login(UserDTO userDto){
        String username = userDto.getUsername();
        UserDTO userInDB = userRepository.selectUserBy(username);

        UserLoginResponse loginResponse = new UserLoginResponse();
        Response response;
        if(userInDB == null) {
            loginResponse.setResponse(new Response(-1,"用户不存在"));
            return loginResponse;
        }

        if(userInDB.getPassword().equals(userDto.getPassword())){
            response = new Response(0,"请求成功");
            loginResponse.setToken(username);
            loginResponse.setUserId(userInDB.getUserId());
            String token = JwtUtil.createToken(userDto);
            loginResponse.setToken(token);

        }else {
            response = new Response(-1,"用户名或密码错误");
        }
        loginResponse.setResponse(response);
        return loginResponse;
    }
}
