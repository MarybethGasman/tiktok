package org.tm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    @JsonProperty("id")
    private Long commentId;

    private Video video;

    private User user;

    private String content;

    @JsonProperty("create_date")
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    public void setUserId(Long userId) {
        if(user == null) {
            user = new User();
        }
        user.setUserId(userId);
    }
    @JsonIgnore
    public Long getUserId() {
        if(user == null) {
            throw new RuntimeException("访问的user不存在");
        }
        return user.getUserId();
    }
    @JsonIgnore
    public void setVideoId(Long userId) {
        if(video == null) {
            video = new Video();
        }
        video.setId(userId);
    }


    public Long getVideoId() {
        if(video == null) {
            throw new RuntimeException("访问的video不存在");
        }
        return video.getId();
    }
}
