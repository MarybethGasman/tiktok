package org.tm.repository;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.tm.pojo.Video;

import java.util.List;

@Mapper
public interface VideoRepository {

    String CACHE_KEY = "MY_KEY";

    /**
     * 查询视频列表，新的视频在前面
     * @param latestTime
     * @return
     */
    @Results(id = "video", value = {
            @Result(property = "author",
                    column = "user_id",
                    one=@One(select="org.tm.repository.UserRepository.selectUserById",
                    fetchType= FetchType.EAGER)),
    })
    @Select("select video_id as id," +
                    "user_id," +
                    "play_url as playUrl," +
                    "cover_url as coverUrl," +
                    "favorite_count as favoriteCount," +
                    "comment_count as commentCount," +
                    "title," +
                    "create_date," +
                    "update_date" +
                    " from video" +
                    //" where create_date <= FROM_UNIXTIME(#{latestTime})" +
                    " order by create_date desc" +
                    " limit 20" +
            "")

    List<Video> GetListSortedByCreateTime(String latestTime);

    @Update("update video set favorite_count = favorite_count + #{i} " +
            "where video_id = #{videoId}")
    @CacheEvict(value = "videos", key = "#root.target.CACHE_KEY")
    void addFavoriteCount(Long videoId, Integer i);

    @CacheEvict(value = "videos", key = "#root.target.CACHE_KEY")
    @Insert("insert into video(user_id,play_url,cover_url,title) " +
            "values(#{userId},#{playUrl},#{coverUrl},#{title})")
    void save(Video video);


    @Results(value = {
            @Result(property = "author",
                    column = "user_id",
                    one=@One(select="org.tm.repository.UserRepository.selectUserById",
                            fetchType= FetchType.EAGER)),
    })
    @Select("<script> " +
            "select video_id as id, user_id, play_url as playUrl, cover_url as coverUrl, " +
            "favorite_count as favoriteCount, comment_count as commentCount,title " +
            "from video where video_id in " +
            "<foreach item='item' index='index' collection='videoIdList' open='(' separator=',' close=')'> " +
            "   #{item} " +
            "</foreach>" +
            "<if test = 'videoIdList != null and videoIdList.size() == 0'>" +
            "(-1)" +
            "</if>" +
            "</script> ")
    List<Video> selectVideoListByVideoIdList(List<Long> videoIdList);


    @Results(value = {
            @Result(property = "author",
                    column = "user_id",
                    one=@One(select="org.tm.repository.UserRepository.selectUserById",
                            fetchType= FetchType.EAGER)),
    })
    @Select("select video_id as id, user_id, play_url as playUrl, cover_url as coverUrl, " +
            "favorite_count as favoriteCount, comment_count as commentCount,title " +
            "from video where user_id = #{userId} ")
    @Cacheable("publish_videos")
    List<Video> getPublishVideoList(Long userId);


    @Update("update video set comment_count = comment_count + #{i} " +
            "where video_id = #{videoId}")
    @CacheEvict("videos")
    void addCommentCount(Long videoId, int i);
}
