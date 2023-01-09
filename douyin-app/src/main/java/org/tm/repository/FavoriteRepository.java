package org.tm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DuplicateKeyException;
import org.tm.po.FavoritePO;

import java.util.List;

@Mapper
public interface FavoriteRepository {

    @Insert("insert into `favorite`(user_id,video_id) values(#{userId},#{videoId})")
    void insert(FavoritePO favoritePO) throws DuplicateKeyException;

    @Update("update favorite set is_deleted = #{isDeleted} " +
            "where user_id = #{favoritePO.userId} and video_id = #{favoritePO.videoId}")
    void updateIsDeleted(FavoritePO favoritePO, boolean isDeleted);


    @Select("select video_id from favorite " +
            "where user_id = #{userId} and is_deleted = 0")
    List<Long> getFavoriteVideoIdList(Long userId);
}
