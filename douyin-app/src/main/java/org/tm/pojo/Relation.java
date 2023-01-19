package org.tm.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Relation implements Serializable {

    private Long Id;

    private User follower;

    private User followee;

    private Boolean isDeleted;

    public void setFollowerId(Long followerId) {
        if(follower == null) {
            follower = new User();
        }
        follower.setUserId(followerId);
    }

    public Long getFollowerId() {
        if(follower == null) {
            throw new RuntimeException("访问不存在的follower");
        }
        return follower.getUserId();
    }

    public Long getFolloweeId() {
        if(follower == null) {
            throw new RuntimeException("访问不存在的followee");
        }
        return followee.getUserId();
    }

    public void setFolloweeId(Long followeeId) {
        if(followee == null) {
            followee = new User();
        }
        followee.setUserId(followeeId);
    }
}
