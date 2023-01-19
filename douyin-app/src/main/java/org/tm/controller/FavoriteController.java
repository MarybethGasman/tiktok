package org.tm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.pojo.Response;
import org.tm.pojo.Video;
import org.tm.service.FavoriteService;
import org.tm.util.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("douyin/favorite")
public class FavoriteController {

    @Value("${video.base}")
    private String videoBaseUrI;

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
        Response response = Response.success();
        return response;
    }

    @RequestMapping("/list")
    public Response FavoriteList(@RequestParam("token") String token,
                                          @RequestParam("user_id") Long userId) {

        Long viewerId = null;
        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }
        List<Video> videoList =
                favoriteService.getFavoriteVideoList(userId, viewerId);


        videoList = videoList.stream().map((videoDTO -> {
            String coverUrl = videoDTO.getCoverUrl();
            String playUrl = videoDTO.getPlayUrl();

            videoDTO.setCoverUrl(videoBaseUrI + coverUrl);
            videoDTO.setPlayUrl(videoBaseUrI + playUrl);
            return videoDTO;
        })).collect(Collectors.toList());

        Response response = Response.success();
        response.put("video_list", videoList);

        return response;
    }
}
