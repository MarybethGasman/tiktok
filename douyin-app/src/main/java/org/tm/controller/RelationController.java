package org.tm.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.dto.Response;
import org.tm.dto.UserDTO;
import org.tm.dto.UserListResponse;
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
    public UserListResponse getFollowList(@RequestParam("user_id") Long userId,
                                          @RequestParam("token") String token) {

        Long viewerId = null;

        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        List<UserDTO> userDTOList = relationService.QueryFollowList(userId, viewerId);

        return new UserListResponse(new Response(0,"查询成功"),userDTOList);
    }

    @RequestMapping(value = "/follower/list",method = RequestMethod.GET)
    public UserListResponse getFollowerList(@RequestParam("user_id") Long userId,
                                          @RequestParam("token") String token) {

        Long viewerId = null;

        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        List<UserDTO> userDTOList = relationService.QueryFollowerList(userId, viewerId);

        return new UserListResponse(new Response(0,"查询成功"),userDTOList);
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
        Response response = new Response(0,"操作成功");
        return response;

    }


}
