package org.tm.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {
    @JsonProperty("id")
    private Long userId;
    private String token;

    @JsonProperty("name")
    private String username;

    private String password;

    @JsonProperty("follow_count")
    private Integer followCount;
    private List<Relation> followList;

    @JsonProperty("follower_count")
    private Integer followerCount;
    private List<Relation> followerList;

    @JsonProperty("is_follow")
    private Boolean isFollow;

}
