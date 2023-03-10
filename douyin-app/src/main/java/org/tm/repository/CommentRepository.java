package org.tm.repository;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.tm.pojo.Comment;

import java.util.Date;
import java.util.List;

@Mapper
public interface CommentRepository {


    @Insert("insert into comment(user_id,video_id,content) " +
            "values(#{userId},#{videoId},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId", keyColumn = "comment_id")
    @CacheEvict(value = "comments", key = "#commentPO.videoId")
    void insert(Comment comment);

    @Update("update comment set is_deleted = #{isDeleted} " +
            "where comment_id = #{comment.commentId}")
    @CacheEvict(value = "comments", key = "#commentPO.videoId")
    void updateIsDeleted(Comment comment, boolean isDeleted);

    @Results(value = {
            @Result(property = "user",
                    column = "user_id",
                    one=@One(select="org.tm.repository.UserRepository.selectUserById",
                            fetchType= FetchType.EAGER)),
            @Result(property = "createDate",
                    column = "create_time",
                    javaType = Date.class)
    })
    @Select("select comment_id, user_id, video_id, content, create_time " +
            "from comment " +
            "where video_id = #{videoId} and is_deleted = 0")
    @Cacheable(value = "comments", key = "#videoId")
    List<Comment> selectCommentListByVideoId(Long videoId);
}
