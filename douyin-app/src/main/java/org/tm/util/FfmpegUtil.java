package org.tm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class FfmpegUtil {

    private static final Logger logger = LoggerFactory.getLogger(FfmpegUtil.class);

    private static String ffmpegPath;

    @Value("${ffmpeg.path}")
    public void setFfmpegPath(String ffmpegPath) {
        FfmpegUtil.ffmpegPath = ffmpegPath;
    }

    public static String getCoverUrl(String filePath) throws IOException {
        logger.info("ffmpeg path is", ffmpegPath);
        return processImgWindows
                (filePath, ffmpegPath);

    }
    public static String processImgWindows(String video_path,String ffmpeg_path) throws IOException {
        File file = new File(video_path);
        if (!file.exists()) {
            return "";
        }
        String imgPath = video_path.substring(0,video_path.lastIndexOf(".")) + ".jpg";
        List<String> commands = new java.util.ArrayList<>();
        commands.add(ffmpeg_path);
        commands.add("-y");
        commands.add("-i");
        commands.add(video_path);
        commands.add("-vframes");
        commands.add("1");//这个参数是设置截取视频多少秒时的画面
        commands.add("-f");
        commands.add("image2");
        commands.add(imgPath);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        builder.start();
        return imgPath;
    }
}
