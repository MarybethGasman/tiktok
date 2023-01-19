package org.tm.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.tm.pojo.Response;
import org.tm.exception.ParameterInvalidException;
import org.tm.pojo.User;
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
    public Response register(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password ){
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            throw new ParameterInvalidException("参数为空");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        authorizeService.register(user);

        String token = JwtUtil.createToken(user);
        Response response = Response.success();
        response.put("token", token);
        response.put("user_id", user.getUserId());
        return response;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestParam("username") String username,
                         @RequestParam("password") String password ){
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            throw new ParameterInvalidException("参数为空");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        authorizeService.login(user);
        Response response = Response.success();
        if(user.getUserId() == null) {
            response = Response.fail(-1, "用户名或密码错误");
        }
        response.put("token", JwtUtil.createToken(user));
        response.put("user_id", user.getUserId());
        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response getUserInfo(@RequestParam("user_id") Long userId,
                                    @RequestParam(value = "token", required = false)
                                            String token) {

        Long viewerId = null;
        if (StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        User userInfo = userService.getUserInfoById(userId);

        boolean isFollow;
        isFollow = relationService.checkIfAIsFollowB(viewerId, userId)
                .orElse(false);
        userInfo.setIsFollow(isFollow);
        Response response = Response.success();

        response.put("user", userInfo);
        return response;
    }

}
