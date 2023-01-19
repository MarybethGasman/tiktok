package org.tm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.tm.pojo.Favorite;

import java.util.List;

@Mapper
public interface FavoriteRepository {

    @Insert("insert into `favorite`(user_id,video_id) values(#{userId},#{videoId})")
    @CacheEvict(value = "favorites", key = "'videos'+ #videoId")
    void insert(Favorite favorite) throws DuplicateKeyException;

    @Update("update favorite set is_deleted = #{isDeleted} " +
            "where  user_id = #{favorite.userId} and video_id = #{favorite.videoId}")
    @CacheEvict(value = "favorites", key = "'videos'+ #videoId")
    void updateIsDeleted(Favorite favorite, boolean isDeleted);


    @Select("select video_id from favorite " +
            "where user_id = #{userId} and is_deleted = 0")
    @Cacheable(value = "favorites", key = "'users' + #userId")
    List<Long> getFavoriteVideoIdList(Long userId);
}
