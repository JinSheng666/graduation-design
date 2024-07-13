package com.jinsheng.mapper;

import com.jinsheng.domain.ForumComment;
import com.jinsheng.vo.VoForumComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumCommentMapper {

    int forumCommentCount(Integer forumId);

    List<VoForumComment> forumCommentByForumId(Integer forumId);

    int addComment(ForumComment forumComment);

    int deleteComment(Integer commentId);

    //根据评论id查询子评论
    List<ForumComment> findParentId(Integer commentId);

    //根据评论id删除子评论
    int deleteCommentParent(Integer commentId);

    //根据评论ID查询文章ID
    int findCommentById(Integer commentId);

    //根据用户ID查询评论
    ForumComment findCommentByUserId(@Param("userId") Integer userId,@Param("commentId") Integer commentId);
    //修改点赞数
    int updateCommentLike(@Param("count") Integer count,@Param("commentId") Integer commentId);

    int forumCommentCountById(Integer forumId);

    //根据文章id删除评论
    int commentDelete(Integer forumId);
    //根据文章id查询评论id
    List<Integer> getCommentByForumId(Integer forumId);
}
