package org.tm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Long userId;

    @JsonProperty("name")
    private String username;

    private String password;

    @JsonProperty("follow_count")
    private Integer followCount;

    @JsonProperty("follower_count")
    private Integer followerCount;

    @JsonProperty("is_follow")
    private boolean isFollow;

    public boolean isFollow() {
        return isFollow;
    }

    @JsonProperty("is_follow")
    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDto = (UserDTO) o;
        return Objects.equals(userId, userDto.userId) && Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(followCount, userDto.followCount) && Objects.equals(followerCount, userDto.followerCount);
    }

    public UserDTO(Long userId, String username, Integer followCount, Integer followerCount, boolean isFollow) {
        this.userId = userId;
        this.username = username;
        this.followCount = followCount;
        this.followerCount = followerCount;
        this.isFollow = isFollow;
    }

    public UserDTO() {
    }
}
