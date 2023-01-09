package org.tm.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.dto.CommentDTO;
import org.tm.dto.CommentListResponse;
import org.tm.dto.CommentResponse;
import org.tm.dto.Response;
import org.tm.po.CommentPO;
import org.tm.service.CommentService;
import org.tm.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/douyin/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/action",method = RequestMethod.POST)
    public CommentResponse commentAction(@RequestParam("token") String token,
                                         @RequestParam("video_id") Long videoId,
                                         @RequestParam("action_type") Integer actionType,
                                         @RequestParam(value = "comment_text", required = false) String content,
                                         @RequestParam(value = "comment_id",required = false)
                              Long commentId) {
        Long userId = JwtUtil.getUserId(token);

        CommentResponse commentResponse = null;
        CommentPO commentPO = new CommentPO();
        commentPO.setUserId(userId);
        commentPO.setVideoId(videoId);
        commentPO.setContent(content);
        commentPO.setCommentId(commentId);

        switch (actionType) {
            case 1:
                commentResponse = commentService.addComment(commentPO);
                break;
            case 2:
                commentResponse = new CommentResponse();
                commentService.removeComment(commentPO);
                break;
            default:break;
        }

        Response response = new Response(0,"操作成功");
        commentResponse.setResponse(response);
        return commentResponse;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommentListResponse getVideoCommentList(@RequestParam("token") String token,
                                                   @RequestParam("video_id") Long videoId) {

        Long userId = null;
        if(StringUtils.hasLength(token)) {
            userId = JwtUtil.getUserId(token);
        }
        CommentListResponse response = new CommentListResponse();

        List<CommentDTO> commentDTOList =
                commentService.queryVideoCommentList(userId,videoId);

        response.setResponse(new Response(0,"请求成功"));
        response.setCommentDTOList(commentDTOList);
        return response;
    }

}
