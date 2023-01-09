package org.tm.repository;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.tm.dto.VideoDTO;
import org.tm.po.VideoPO;

import java.util.List;

@Mapper
public interface VideoRepository {

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
    List<VideoDTO> GetListSortedByCreateTime(String latestTime);

    @Update("update video set favorite_count = favorite_count + #{i} " +
            "where video_id = #{videoId}")
    void addFavoriteCount(Long videoId, Integer i);

    @Insert("insert into video(user_id,play_url,cover_url,title) " +
            "values(#{userId},#{playUrl},#{coverUrl},#{title})")
    void save(VideoPO videoPO);


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
    List<VideoDTO> selectVideoListByVideoIdList(List<Long> videoIdList);


    @Results(value = {
            @Result(property = "author",
                    column = "user_id",
                    one=@One(select="org.tm.repository.UserRepository.selectUserById",
                            fetchType= FetchType.EAGER)),
    })
    @Select("select video_id as id, user_id, play_url as playUrl, cover_url as coverUrl, " +
            "favorite_count as favoriteCount, comment_count as commentCount,title " +
            "from video where user_id = #{userId} ")
    List<VideoDTO> getPublishVideoList(Long userId);


    @Update("update video set comment_count = comment_count + #{i} " +
            "where video_id = #{videoId}")
    void addCommentCount(Long videoId, int i);
}
