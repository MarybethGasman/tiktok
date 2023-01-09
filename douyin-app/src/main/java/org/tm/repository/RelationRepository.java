package org.tm.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.tm.po.RelationPO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RelationRepository {

    @Select("SELECT not is_deleted from relation " +
            "where follower_id = #{followerId} and followee_id = #{celebrityId}")
    Boolean selectFollowStatus(Long followerId, Long celebrityId);


    @Select("select followee_id from relation " +
            "where follower_id = #{userId} and is_deleted = 0")
    List<Long> getFollowingUserIdList(Long userId);

    @Insert("insert into relation(follower_id, followee_id) " +
            "values(#{followerId},#{followeeId})")
    void insert(RelationPO relationPO);

    @Update("update relation set is_deleted = #{isDeleted}")
    void updateIsDeleted(RelationPO relationPO, boolean isDeleted);

    @Select("select follower_id from relation " +
            "where followee_id = #{userId} and is_deleted = 0")
    List<Long> getFollowerUserIdList(Long userId);
}
