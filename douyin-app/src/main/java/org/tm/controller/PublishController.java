package org.tm.controller;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tm.dto.Response;
import org.tm.dto.VideoDTO;
import org.tm.dto.VideoListResponse;
import org.tm.po.VideoPO;
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

        String fileName = uuid + fileSuffix;
        String filePath = ResourceUtils.getURL("classpath:").getPath() + "public/" + fileName;
        filePath = filePath.substring(1);
        file.transferTo(new File(filePath));


        String coverUrl = fileName.substring(0,fileName.lastIndexOf(".")) + ".jpg";
        VideoPO videoPO = new VideoPO();
        videoPO.setTitle(title);
        videoPO.setPlayUrl(fileName);
        videoPO.setCoverUrl(coverUrl);
        videoPO.setUserId(userId);

        FfmpegUtil.getCoverUrl(filePath);
        videoService.addVideo(videoPO);

        return new Response(0,"上传成功");
    }

    @RequestMapping("/list")
    public VideoListResponse GetPublishVideoList(@RequestParam("token") String token,
                                                 @RequestParam("user_id") Long userId) {
        Long viewerId = null;
        if(StringUtils.hasLength(token)) {
            viewerId = JwtUtil.getUserId(token);
        }

        List<VideoDTO> videoDTOList =
                videoService.getPublishVideoList(userId, viewerId);

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
