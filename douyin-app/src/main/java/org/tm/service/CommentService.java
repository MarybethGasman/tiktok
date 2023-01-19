package org.tm.service;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tm.pojo.Comment;
import org.tm.pojo.User;
import org.tm.repository.CommentRepository;
import org.tm.repository.RelationRepository;
import org.tm.repository.UserRepository;
import org.tm.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final VideoRepository videoRepository;

    private final UserRepository userRepository;

    private final RelationRepository relationRepository;

    public CommentService(CommentRepository commentRepository, VideoRepository videoRepository, UserRepository userRepository, RelationRepository relationRepository) {
        this.commentRepository = commentRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
    }

    public void addComment(Comment comment) {
        Long userId = comment.getUser().getUserId();
        User speaker = userRepository.selectUserById(userId);
        comment.setUser(speaker);
        try {
            commentRepository.insert(comment);
        }catch (DuplicateKeyException e) {
            // FIXME: 2023/1/19 comment.commentId 为 null
            commentRepository.updateIsDeleted(comment,false);
        }
        Long videoId = comment.getVideoId();
        videoRepository.addCommentCount(videoId, 1);
    }

    public void removeComment(Comment comment) {
        Long videoId = comment.getVideo().getId();

        commentRepository.updateIsDeleted(comment,true);
        videoRepository.addCommentCount(videoId, -1);

    }

    public List<Comment> queryVideoCommentList(Long userId, Long videoId) {

        List<Comment> commentList =
                commentRepository.selectCommentListByVideoId(videoId);

        if(userId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(userId))
                            .orElse(new ArrayList<>());

            commentList = commentList.stream().map((comment -> {
                User speaker = comment.getUser();
                speaker.setIsFollow(followingIdList.contains(speaker.getUserId()));
                comment.setVideo(null);
                return comment;
            })).collect(Collectors.toList());
        }
        return commentList;
    }
}
