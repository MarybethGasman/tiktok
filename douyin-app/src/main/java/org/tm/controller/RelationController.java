package org.tm.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.pojo.Response;
import org.tm.pojo.User;
import org.tm.service.RelationService;
import org.tm.util.JwtUtil;

import java.util.List;


@RestController
@RequestMapping("douyin/relation")
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @RequestMapping(value = "/follow/list",method = RequestMethod.GET)
    public Response getFollowList(@RequestParam("user_id") Long userId,
                                              @RequestParam("token") String token) {

        Long viewerId = null;

        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        List<User> userDTOList = relationService.QueryFollowList(userId, viewerId);

        Response response = Response.success();
        response.put("user_list", userDTOList);
        return response;
    }

    @RequestMapping(value = "/follower/list",method = RequestMethod.GET)
    public Response getFollowerList(@RequestParam("user_id") Long userId,
                                          @RequestParam("token") String token) {

        Long viewerId = null;

        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        List<User> userDTOList = relationService.QueryFollowerList(userId, viewerId);

        Response response = Response.success();
        response.put("user_list", userDTOList);
        return response;
    }

    @RequestMapping(value = "/action",method = RequestMethod.POST)
    public Response followAction(@RequestParam("token") String token,
                                 @RequestParam("to_user_id") Long toUserId,
                                 @RequestParam("action_type") Integer actionType) {

        Long userId = JwtUtil.getUserId(token);

        switch (actionType) {
            case 1:
                relationService.addRelation(userId,toUserId);
                break;
            case 2:
                relationService.removeRelation(userId,toUserId);
                break;
            default:break;
        }
        Response response = Response.success();
        return response;
    }


}
