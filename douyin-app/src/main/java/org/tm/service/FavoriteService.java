package org.tm.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.tm.dto.UserDTO;
import org.tm.dto.VideoDTO;
import org.tm.po.FavoritePO;
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
        FavoritePO favoritePO = new FavoritePO();
        favoritePO.setUserId(userId);
        favoritePO.setVideoId(videoId);
        try {
            favoriteRepository.insert(favoritePO);
        }catch (DuplicateKeyException e) {
            favoriteRepository.updateIsDeleted(favoritePO,false);
        }
        videoRepository.addFavoriteCount(videoId, 1);
    }


    public void removeFavorite(Long userId, Long videoId) {
        FavoritePO favoritePO = new FavoritePO();
        favoritePO.setUserId(userId);
        favoritePO.setVideoId(videoId);
        favoriteRepository.updateIsDeleted(favoritePO,true);
        videoRepository.addFavoriteCount(videoId, -1);
    }

    public List<VideoDTO> getFavoriteVideoList(Long userId, Long viewerId) {

        List<Long> favoriteVideoIdList =
                favoriteRepository.getFavoriteVideoIdList(userId);

        List<VideoDTO> videoList = videoRepository
                .selectVideoListByVideoIdList(favoriteVideoIdList);
        if(viewerId != null) {

            final List<Long> followingIdList =
                    Optional.ofNullable(relationRepository.getFollowingUserIdList(viewerId))
                            .orElse(new ArrayList<>());

            final List<Long> viewerFavoriteVideoIdList =
                    favoriteRepository.getFavoriteVideoIdList(viewerId);

            videoList = videoList.stream().map((videoDTO -> {
                videoDTO.setFavorite(viewerFavoriteVideoIdList.contains(videoDTO.getId()));
                UserDTO author = videoDTO.getAuthor();
                author.setFollow(followingIdList.contains(author.getUserId()));
                return videoDTO;
            })).collect(Collectors.toList());
        }
        return videoList;
    }
}
