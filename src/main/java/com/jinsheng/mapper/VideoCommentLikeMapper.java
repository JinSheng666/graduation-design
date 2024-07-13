package com.jinsheng.mapper;

import com.jinsheng.domain.VideoCommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoCommentLikeMapper {

    VideoCommentLike likeStatusById(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    int addVideoCommentLike(@Param("commentId") Integer commentId,@Param("userId") Integer userId);

    int countByCommentId(Integer commentId);

    int updateVideoCommentLike(@Param("status") Integer status,@Param("vclId") Integer vclId);

    int delCommentLikeByCommentId(List<Integer> ids);
}
