package org.tm.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Favorite implements Serializable {
    private User user;

    private Video video;

    private boolean isDeleted;

    public void setUserId(Long userId) {
        if(user == null) {
            user = new User();
        }
        user.setUserId(userId);
    }

    public Long getUserId() {
        if(user == null) {
            throw new RuntimeException("访问的user不存在");
        }
        return user.getUserId();
    }

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
