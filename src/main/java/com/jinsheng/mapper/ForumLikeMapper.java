package com.jinsheng.mapper;

import com.jinsheng.domain.ForumLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumLikeMapper {
    //添加
    int addForumLike(@Param("forumId") Integer forumId, @Param("userId")Integer userId);
    //修改状态
    int updateForumLike(@Param("status") Integer status,@Param("likeId") Integer likeId);
    //查询like
    ForumLike likeStatusById(@Param("userId") Integer userId, @Param("forumId") Integer forumId);
    //计数查询当前点赞
    int countByForumId(Integer forumId);
    //根据文章id删除
    int likeDelByForumId(Integer forumId);

    List<Integer> forumLikeCurrentUser(Integer userId);
}
