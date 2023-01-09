package org.tm.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.dto.Response;
import org.tm.dto.VideoDTO;
import org.tm.dto.VideoListResponse;
import org.tm.service.FavoriteService;
import org.tm.util.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("douyin/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @RequestMapping("/action")
    public Response favoriteAction(@RequestParam("token") String token,
                                   @RequestParam("video_id") Long videoId,
                                   @RequestParam("action_type") Integer actionType) {
        Long userId = JwtUtil.getUserId(token);

        switch (actionType) {
            case 1:
                favoriteService.addFavorite(userId,videoId);
                break;
            case 2: favoriteService.removeFavorite(userId,videoId);break;
            default:break;
        }
        Response response = new Response(0,"操作成功");
        return response;
    }

    @RequestMapping("/list")
    public VideoListResponse FavoriteList(@RequestParam("token") String token,
                                          @RequestParam("user_id") Long userId) {

        Long viewerId = null;
        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }
        List<VideoDTO> videoDTOList =
                favoriteService.getFavoriteVideoList(userId, viewerId);

        String baseUrl = "http://124.223.112.154:9091/static/";

        videoDTOList = videoDTOList.stream().map((videoDTO -> {
            String coverUrl = videoDTO.getCoverUrl();
            String playUrl = videoDTO.getPlayUrl();

            videoDTO.setCoverUrl(baseUrl + coverUrl);
            videoDTO.setPlayUrl(baseUrl + playUrl);
            return videoDTO;
        })).collect(Collectors.toList());

        return new VideoListResponse(new Response(0,"请求成功")
                ,null,videoDTOList);
    }
}
