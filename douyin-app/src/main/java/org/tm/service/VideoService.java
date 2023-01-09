package org.tm.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.tm.dto.UserDTO;
import org.tm.dto.VideoDTO;
import org.tm.po.VideoPO;
import org.tm.repository.FavoriteRepository;
import org.tm.repository.RelationRepository;
import org.tm.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final FavoriteRepository favoriteRepository;
    private final RelationRepository relationRepository;

    public VideoService(VideoRepository videoRepository, FavoriteRepository favoriteRepository, RelationRepository relationRepository) {
        this.videoRepository = videoRepository;
        this.favoriteRepository = favoriteRepository;
        this.relationRepository = relationRepository;
    }

    public List<VideoDTO> getAllVideoList(String latestTime, Long userId) {

        List<VideoDTO> videoList = videoRepository.GetListSortedByCreateTime(latestTime);

        if(userId != null) {
            final List<Long> favoriteVideoIdList =
                    favoriteRepository.getFavoriteVideoIdList(userId);

            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(userId))
                            .orElse(new ArrayList<>());

            videoList = videoList.stream().map((videoDTO -> {
                videoDTO.setFavorite(favoriteVideoIdList.contains(videoDTO.getId()));
                UserDTO author = videoDTO.getAuthor();
                author.setFollow(followingIdList.contains(author.getUserId()));
                return videoDTO;
            })).collect(Collectors.toList());
        }

        return videoList;

    }

    public void addVideo(VideoPO videoPO) {
        videoRepository.save(videoPO);
    }

    public List<VideoDTO> getPublishVideoList(Long userId, Long viewerId) {

        List<VideoDTO> publishVideoList =
                videoRepository.getPublishVideoList(userId);

        if(viewerId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            final List<Long> viewerFavoriteVideoIdList =
                    favoriteRepository.getFavoriteVideoIdList(viewerId);

            publishVideoList = publishVideoList.stream().map((videoDTO -> {
                videoDTO.setFavorite(viewerFavoriteVideoIdList.contains(videoDTO.getId()));
                UserDTO author = videoDTO.getAuthor();
                author.setFollow(followingIdList.contains(author.getUserId()));
                return videoDTO;
            })).collect(Collectors.toList());
        }
        return publishVideoList;

    }

}
