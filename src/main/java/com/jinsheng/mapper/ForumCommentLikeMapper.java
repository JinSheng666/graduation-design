package com.jinsheng.mapper;

import com.jinsheng.domain.ForumCommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumCommentLikeMapper {

    //添加
    int addForumCommentLike(@Param("commentId") Integer commentId,@Param("userId") Integer userId);
    //修改状态
    int updateForumCommentLike(@Param("status") Integer status, @Param("commentLikeId") Integer commentLikeId);
    //查询like
    ForumCommentLike likeStatusById(@Param("userId") Integer userId, @Param("commentId") Integer commentId);
    //计数查询当前点赞
    int countByCommentId(Integer commentId);
    //根据评论id删除点赞
    int commentLikeDelByCommentId(List<Integer> ids);

}
