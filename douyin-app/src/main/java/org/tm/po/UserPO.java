package org.tm.po;


public class UserPO {
    private Long userId;

    private String username;

    private String password;

    private Integer followCount;

    private Integer followerCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public UserPO(Long userId, String username, String password, Integer followCount, Integer followerCount) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.followCount = followCount;
        this.followerCount = followerCount;
    }

    public UserPO() {
    }

    public UserPO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
