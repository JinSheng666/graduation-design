package com.jinsheng.mapper;

import com.jinsheng.domain.VideoComment;
import com.jinsheng.vo.VoVideoComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoCommentMapper {

    int CommentCountByVideoId(Integer videoId);

    List<VoVideoComment> CommentByVideoId(Integer videoId);

    int addComment(VideoComment videoComment);

    VideoComment findCommentByVCId(Integer commentId);

    int deleteComment(@Param("commentId") Integer commentId,@Param("userId") Integer userId);
    //修改点赞数
    int updateCommentLike(@Param("count") Integer count,@Param("commentId") Integer commentId);

    int deleteCommentById(Integer commentId);

    int deleteParentByCommentId(Integer commentId);

    int delCommentByVideoId(Integer videoId);

    List<Integer> getCommentIdsByVideoId(Integer videoId);
}
