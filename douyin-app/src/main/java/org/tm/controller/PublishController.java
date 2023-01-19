package org.tm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tm.pojo.Response;
import org.tm.pojo.Video;
import org.tm.service.FileSystemStorageService;
import org.tm.service.VideoService;
import org.tm.util.JwtUtil;
import org.tm.util.FfmpegUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("douyin/publish")
public class PublishController {

    private final VideoService videoService;

    @Value("${video.base}")
    private String videoBaseUrI;

    public PublishController(VideoService videoService) {
        this.videoService = videoService;
    }

    @RequestMapping("/action")
    public Response publishAction(@RequestParam("data")MultipartFile file,
                                  @RequestParam("token") String token,
                                  @RequestParam("title") String title) throws IOException {

        Long userId = JwtUtil.getUserId(token);

        // 获取文件的名称
        String originalFilename = file.getOriginalFilename();
        // 文件后缀 例如：.png
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // uuid 生成文件名
        String uuid = String.valueOf(UUID.randomUUID());

        FileSystemStorageService systemStorageService = new FileSystemStorageService();
        String fileName = uuid + fileSuffix;
        systemStorageService.store(file, fileName);


        String coverUrl = fileName.substring(0,fileName.lastIndexOf(".")) + ".jpg";
        Video video = new Video();
        video.setTitle(title);
        video.setPlayUrl(fileName);
        video.setCoverUrl(coverUrl);
        video.setUserId(userId);

        videoService.addVideo(video);

        return Response.success();
    }

    @RequestMapping("/list")
    public Response GetPublishVideoList(@RequestParam("token") String token,
                                                 @RequestParam("user_id") Long userId) {
        Long viewerId = null;
        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        List<Video> videoList =
                videoService.getPublishVideoList(userId, viewerId);

        videoList = videoList.stream().map((video -> {
            String coverUrl = video.getCoverUrl();
            String playUrl = video.getPlayUrl();

            video.setCoverUrl(videoBaseUrI + coverUrl);
            video.setPlayUrl(videoBaseUrI + playUrl);
            return video;
        })).collect(Collectors.toList());

        Response response = Response.success();
        response.put("video_list", videoList);
        return response;
    }
}
