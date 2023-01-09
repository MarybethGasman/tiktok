package org.tm.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tm.dto.Response;
import org.tm.dto.VideoDTO;
import org.tm.dto.VideoListResponse;
import org.tm.repository.RelationRepository;
import org.tm.service.FavoriteService;
import org.tm.service.VideoService;
import org.tm.util.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("douyin/feed")
public class VideoController {

    private final VideoService videoService;
    public VideoController(VideoService videoService, FavoriteService favoriteService, RelationRepository relationRepository) {
        this.videoService = videoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public VideoListResponse getAllVideoList(@RequestParam(value = "latest_time", required = false) String time,
                                             @RequestParam(value = "token", required = false) String token) {

        if (time == null) {
            time = Long.toString(System.currentTimeMillis()/1000L);
        }

        Long userId = null;
        if (StringUtils.hasLength(token)) {
            userId = JwtUtil.getUserId(token);
        }

        List<VideoDTO> videoList;
        videoList = videoService.getAllVideoList(time, userId);

        String baseUrl = "http://124.223.112.154:9091/static/";

        videoList = videoList.stream().map((videoDTO -> {
            String coverUrl = videoDTO.getCoverUrl();
            String playUrl = videoDTO.getPlayUrl();

            videoDTO.setCoverUrl(baseUrl + coverUrl);
            videoDTO.setPlayUrl(baseUrl + playUrl);
            return videoDTO;
        })).collect(Collectors.toList());

        VideoListResponse result = new
                VideoListResponse(new Response(0, "请求成功"),
                1655973624, videoList);

        return result;
    }


}
