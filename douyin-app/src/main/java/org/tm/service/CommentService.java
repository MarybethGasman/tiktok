package org.tm.service;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tm.dto.CommentDTO;
import org.tm.dto.CommentResponse;
import org.tm.dto.UserDTO;
import org.tm.po.CommentPO;
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

    public CommentResponse addComment(CommentPO commentPO) {
        CommentResponse response = new CommentResponse();

        Long userId = commentPO.getUserId();
        UserDTO speaker = userRepository.selectUserById(userId);

        try {
            commentRepository.insert(commentPO);
        }catch (DuplicateKeyException e) {
            commentRepository.updateIsDeleted(commentPO,false);
        }
        Long videoId = commentPO.getVideoId();
        videoRepository.addCommentCount(videoId, 1);

        response.setComment(new CommentDTO(
                commentPO.getCommentId(),
                speaker,
                commentPO.getContent(),
                commentPO.getCreateTime()
        ));
        return response;
    }

    public void removeComment(CommentPO commentPO) {
        Long videoId = commentPO.getVideoId();

        commentRepository.updateIsDeleted(commentPO,true);
        videoRepository.addFavoriteCount(videoId, -1);

    }

    public List<CommentDTO> queryVideoCommentList(Long userId, Long videoId) {

        List<CommentDTO> commentDTOList =
                commentRepository.selectCommentListByVideoId(videoId);

        if(userId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(userId))
                            .orElse(new ArrayList<>());

            commentDTOList = commentDTOList.stream().map((commentDTO -> {
                UserDTO speaker = commentDTO.getUser();
                speaker.setFollow(followingIdList.contains(speaker.getUserId()));
                return commentDTO;
            })).collect(Collectors.toList());
        }
        return commentDTOList;
    }
}
