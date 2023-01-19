package org.tm.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tm.pojo.Favorite;
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
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final VideoRepository videoRepository;

    private final RelationRepository relationRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, VideoRepository videoRepository, RelationRepository relationRepository) {
        this.favoriteRepository = favoriteRepository;
        this.videoRepository = videoRepository;
        this.relationRepository = relationRepository;
    }

    public void addFavorite(Long userId, Long videoId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setVideoId(videoId);
        try {
            favoriteRepository.insert(favorite);
        }catch (DuplicateKeyException e) {
            favoriteRepository.updateIsDeleted(favorite,false);
        }
        videoRepository.addFavoriteCount(videoId, 1);
    }


    public void removeFavorite(Long userId, Long videoId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setVideoId(videoId);
        favoriteRepository.updateIsDeleted(favorite,true);
        videoRepository.addFavoriteCount(videoId, -1);
    }

    public List<Video> getFavoriteVideoList(Long userId, Long viewerId) {

        List<Long> favoriteVideoIdList =
                favoriteRepository.getFavoriteVideoIdList(userId);

        List<Video> videoList = videoRepository
                .selectVideoListByVideoIdList(favoriteVideoIdList);
        if(viewerId != null) {

            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            final List<Long> viewerFavoriteVideoIdList =
                    favoriteRepository.getFavoriteVideoIdList(viewerId);

            videoList = videoList.stream().map((videoDTO -> {
                videoDTO.setIsFavorite(viewerFavoriteVideoIdList.contains(videoDTO.getId()));
                User author = videoDTO.getAuthor();
                author.setIsFollow(followingIdList.contains(author.getUserId()));
                return videoDTO;
            })).collect(Collectors.toList());
        }
        return videoList;
    }
}
