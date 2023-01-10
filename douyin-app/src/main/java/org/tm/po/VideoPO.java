package org.tm.po;


public class VideoPO {
    private Long videoId;

    private Long userId;

    private String playUrl;

    private String coverUrl;

    private Integer favoriteCount;

    private Integer commentCount;

    private String title;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }


    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoPO{" +
                "videoId=" + videoId +
                ", userId=" + userId +
                ", playUrl='" + playUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", commentCount=" + commentCount +
                ", title='" + title + '\'' +
                '}';
    }
}
