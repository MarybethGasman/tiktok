package org.tm.pojo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.stream.events.Comment;
import java.io.Serializable;
import java.util.List;

@Data
public class Video implements Serializable {
    private Long id;
    private String title;

    private User author;

    @JsonProperty("play_url")
    private String playUrl;
    @JsonProperty("cover_url")
    private String coverUrl;

    @JsonProperty("favorite_count")
    private Integer favoriteCount;
    private List<Favorite> favorites;

    @JsonProperty("comment_count")
    private Integer commentCount;
    private List<Comment> comments;

    @JsonProperty("is_favorite")
    private Boolean isFavorite;

    public void setUserId(Long userId) {
        if(author == null) {
            author = new User();
        }
        author.setUserId(userId);
    }

    @JsonIgnore
    public Long getUserId() {
        if(author == null) {
            throw new RuntimeException("访问的user不存在");
        }
        return author.getUserId();
    }

}
