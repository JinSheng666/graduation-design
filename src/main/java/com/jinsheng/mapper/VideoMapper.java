package com.jinsheng.mapper;

import com.jinsheng.common.Page;
import com.jinsheng.domain.Video;
import com.jinsheng.dto.DtoVideo;
import com.jinsheng.vo.VoVideo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoMapper {

    int videoSearchCount(String search);

    List<VoVideo> videoSearch(@Param("search") String search,@Param("page") Page page);

    int videoPageCount(Video video);

    List<Video> videoPage(@Param("video") Video video,@Param("page") Page page);

    int addVideo(DtoVideo video);

    int deleteVideo(Integer videoId);

    String findVideoById(Integer videoId);

    int videoIsPrivacy(@Param("IsPrivacy") Integer IsPrivacy ,@Param("videoId") Integer videoId);

    int updateVideo(DtoVideo dtoVideo);

    DtoVideo idFindVideo(Integer videoId);
    //修改评论数
    int updateCommentCount(@Param("count") Integer count,@Param("videoId") Integer videoId);
    //修改点赞数
    int updateLikeCount(@Param("count") Integer count,@Param("videoId") Integer videoId);

    List<VoVideo> findVideoByUserId(Integer userId);

    int findVideoCountByUserId(Integer userId);

    List<VoVideo> findPrivacyVideoByUserId(Integer userId);

    int IsPrivacy(Integer videoId);

    List<Integer> getVideoUserIds(List<Integer> ids);

    List<VoVideo> getVideoByIds(@Param("videoIdList") List<Integer> videoIdList,@Param("userIdList") List<Integer> userIdList);


}
