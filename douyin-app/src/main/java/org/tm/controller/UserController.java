package org.tm.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.tm.dto.Response;
import org.tm.dto.UserDTO;
import org.tm.dto.UserLoginResponse;
import org.tm.dto.UserResponse;
import org.tm.exception.ParameterInvalidException;
import org.tm.service.AuthorizeService;
import org.tm.service.RelationService;
import org.tm.service.UserService;
import org.tm.util.JwtUtil;

@RestController()
@RequestMapping("douyin/user")
public class UserController {

    private final AuthorizeService authorizeService;

    private final UserService userService;

    private final RelationService relationService;

    public UserController(AuthorizeService authorizeService, UserService userService, RelationService relationService) {
        this.authorizeService = authorizeService;
        this.userService = userService;
        this.relationService = relationService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserLoginResponse register(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password ){
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            throw new ParameterInvalidException("参数为空");
        }
        UserDTO userDto = new UserDTO();
        userDto.setUsername(username);
        userDto.setPassword(password);
        authorizeService.register(userDto);

        String token = JwtUtil.createToken(userDto);
        UserLoginResponse response = new UserLoginResponse();
        response.setToken(token);
        response.setUserId(userDto.getUserId());
        response.setResponse(new Response(0,"注册成功"));
        return response;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginResponse login(@RequestParam("username") String username,
                         @RequestParam("password") String password ){
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            throw new ParameterInvalidException("参数为空");
        }

        UserDTO userDto = new UserDTO();
        userDto.setUsername(username);
        userDto.setPassword(password);

        UserLoginResponse response = authorizeService.login(userDto);
        userDto.setUserId(response.getUserId());

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public UserResponse getUserInfo(@RequestParam("user_id") Long userId,
                                    @RequestParam(value = "token", required = false)
                                            String token) {

        Long viewerId = null;
        if (StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        UserDTO userDTO;
        UserDTO userInfo = userService.getUserInfoById(userId);

        boolean isFollow;
        isFollow = relationService.checkIfAIsFollowB(viewerId, userId)
                .orElse(false);

        UserResponse userResponse = new UserResponse();
        Response response = new Response(0, "请求成功");
        userResponse.setResponse(response);
        userDTO = new UserDTO(
                userInfo.getUserId(),
                userInfo.getUsername(),
                userInfo.getFollowCount(),
                userInfo.getFollowerCount(),
                isFollow
        );
        userResponse.setUser(userDTO);
        return userResponse;
    }

}
