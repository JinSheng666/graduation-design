package com.jinsheng.mapper;

import com.jinsheng.common.Page;
import com.jinsheng.domain.Forum;
import com.jinsheng.vo.VoForum;
import com.jinsheng.vo.VoForumId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumMapper {

    int forumCount(@Param("forum") Forum forum);

    List<Forum> forumPage(@Param("forum") Forum forum, @Param("page") Page page);

    int forumSearchCount(String search);

    List<VoForum> forumSearch(@Param("search") String search,@Param("page") Page page);

    int addForum(Forum forum);

    List<Forum> findAll();

    int deleteForum(Integer forumId);

    int updateForum(VoForumId forum);

    VoForumId selectById(Integer id);

    int updatePrivacy(Integer id);

    int updatePopular(Integer id);
    //评论数量
    int updateComment(@Param("commentCount") Integer commentCount,@Param("forumId") Integer forumId);
    //根据用户ID查询文章
    Forum findForumByUserId(@Param("userId") Integer userId,@Param("forumId") Integer forumId);
    //根据文章ID修改点赞
    int updateLike(@Param("likeCount") Integer likeCount,@Param("forumId") Integer forumId);

    List<VoForum> findFormByUserId(Integer userId);

    int findFormCountByUserId(Integer userId);

    int findPrivacyFormCountByUserId(Integer userId);

    int forumDelById(Integer forumId);

    List<VoForum> findPrivacyFormByUserId(Integer userId);

    List<Integer> getForumUserIds(List<Integer> ids);

    List<VoForum> getForumByIds(@Param("forumIdList") List<Integer> forumIdList,@Param("userIdList") List<Integer> userIdList);



}
