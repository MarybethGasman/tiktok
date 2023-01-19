package org.tm.service;

import org.springframework.stereotype.Service;
import org.tm.pojo.User;
import org.tm.pojo.Video;
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

    public List<Video> getAllVideoList(String latestTime, Long userId) {

        List<Video> videoList = videoRepository.GetListSortedByCreateTime(latestTime);

        if(userId != null) {
            final List<Long> favoriteVideoIdList =
                    favoriteRepository.getFavoriteVideoIdList(userId);

            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(userId))
                            .orElse(new ArrayList<>());

            videoList = videoList.stream().map((video -> {
                video.setIsFavorite(favoriteVideoIdList.contains(video.getId()));
                User author = video.getAuthor();
                author.setIsFollow(followingIdList.contains(author.getUserId()));
                return video;
            })).collect(Collectors.toList());
        }

        return videoList;

    }

    public void addVideo(Video video) {
        videoRepository.save(video);
    }

    public List<Video> getPublishVideoList(Long userId, Long viewerId) {

        List<Video> publishVideoList =
                videoRepository.getPublishVideoList(userId);

        if(viewerId != null) {
            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            final List<Long> viewerFavoriteVideoIdList =
                    favoriteRepository.getFavoriteVideoIdList(viewerId);

            publishVideoList = publishVideoList.stream().map((video -> {
                video.setIsFavorite(viewerFavoriteVideoIdList.contains(video.getId()));
                User author = video.getAuthor();
                author.setIsFollow(followingIdList.contains(author.getUserId()));
                return video;
            })).collect(Collectors.toList());
        }
        return publishVideoList;

    }

}
