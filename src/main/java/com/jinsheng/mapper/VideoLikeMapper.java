package com.jinsheng.mapper;

import com.jinsheng.domain.VideoLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoLikeMapper {

    VideoLike likeStatusById(@Param("userId") Integer userId,@Param("videoId") Integer videoId);

    int addVideoLike(@Param("userId") Integer userId,@Param("videoId") Integer videoId);

    int countByVideoId(Integer videoId);

    int updateVideoLike(@Param("status") Integer status,@Param("vlId") Integer vlId);
    //根据视频id删除点赞
    int delLikeVideoId(Integer videoId);

    List<Integer> videoLikeCurrentUser(Integer userId);
}
