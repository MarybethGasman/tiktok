package org.tm.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.pojo.Response;
import org.tm.pojo.Video;
import org.tm.repository.RelationRepository;
import org.tm.service.FavoriteService;
import org.tm.service.VideoService;
import org.tm.util.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("douyin/feed")
public class VideoController {

    @Value("${video.base}")
    private String videoBaseUrI;

    private final VideoService videoService;
    public VideoController(VideoService videoService, FavoriteService favoriteService, RelationRepository relationRepository) {
        this.videoService = videoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAllVideoList(@RequestParam(value = "latest_time", required = false) String time,
                                                @RequestParam(value = "token", required = false) String token) {
        if (time == null) {
            time = Long.toString(System.currentTimeMillis()/1000L);
        }

        Long userId = null;
        if (StringUtils.hasLength(token)) {
            userId = JwtUtil.getUserId(token);
        }

        List<Video> videoList;
        videoList = videoService.getAllVideoList(time, userId);

        videoList = videoList.stream().map((video -> {
            String coverUrl = video.getCoverUrl();
            String playUrl = video.getPlayUrl();

            video.setCoverUrl(videoBaseUrI + coverUrl);
            video.setPlayUrl(videoBaseUrI + playUrl);

            return video;
        })).collect(Collectors.toList());

        Response result = Response.success();
        result.put("next_time", 0);
        result.put("video_list", videoList);

        return result;
    }


}
