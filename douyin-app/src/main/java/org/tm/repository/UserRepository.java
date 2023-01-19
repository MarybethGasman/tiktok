package org.tm.repository;

import org.apache.ibatis.annotations.*;
import org.tm.pojo.User;

import java.util.List;

@Mapper
public interface UserRepository {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return
     */
    @Results(id = "userMap",value = {
            @Result(property = "username", column = "name"),
    })
    @ResultType(User.class)
    @Select("select user_id,name,password from user where name = #{username}")
    User selectUserBy(String username);

    /**
     * 新增一个用户
     * @param user
     */
    @Insert("insert into user(name,password) values(#{username},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    void insertUser(User user);


    @ResultMap("userMap")
    @Select("select user_id,name,follow_count,follower_count from user " +
            "where user_id = #{id}")
    User selectUserById(Long id);

    @ResultMap("userMap")
    @Select("<script> " +
            "select user_id,name,follow_count,follower_count from user " +
            "where user_id in " +
            "<foreach item='item' index='index' collection='userIdList' open='(' separator=',' close=')'> " +
            "   #{item} " +
            "</foreach>" +
            "<if test = 'userIdList != null and userIdList.size() == 0'>" +
            "(-1)" +
            "</if>" +
            "</script> ")
    List<User> selectUserListByUserIdList(List<Long> userIdList);

    @Update("update user set follow_count = follow_count + #{i} " +
            "where user_id = #{userId}")
    void addFollowCount(Long userId, int i);

    @Update("update user set follower_count = follower_count + #{i} " +
            "where user_id = #{toUserId}")
    void addFollowerCount(Long toUserId, int i);
}
