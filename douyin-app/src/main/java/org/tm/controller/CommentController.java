package org.tm.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.pojo.Response;
import org.tm.pojo.Comment;
import org.tm.service.CommentService;
import org.tm.util.JwtUtil;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/douyin/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/action",method = RequestMethod.POST)
    public Response commentAction(@RequestParam("token") String token,
                                         @RequestParam("video_id") Long videoId,
                                         @RequestParam("action_type") Integer actionType,
                                         @RequestParam(value = "comment_text", required = false) String content,
                                         @RequestParam(value = "comment_id",required = false)
                              Long commentId) {
        Long userId = JwtUtil.getUserId(token);


        Comment comment = new Comment();
        comment.setVideoId(videoId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreateDate(new Date(System.currentTimeMillis()));

        switch (actionType) {
            case 1:
                commentService.addComment(comment);
                break;
            case 2:
                comment.setCommentId(commentId);
                commentService.removeComment(comment);
                break;
            default:break;
        }
        comment.setVideo(null);
        Response response = Response.success();
        response.put("comment", comment);
        return response;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Response getVideoCommentList(@RequestParam("token") String token,
                                                   @RequestParam("video_id") Long videoId) {

        Long userId = null;
        if(StringUtils.hasLength(token)) {
            userId = JwtUtil.getUserId(token);
        }

        List<Comment> commentList =
                commentService.queryVideoCommentList(userId,videoId);

        Response response = Response.success();
        response.put("comment_list", commentList);
        return response;
    }

}
