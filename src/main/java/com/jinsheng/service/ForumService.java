package com.jinsheng.service;

import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Forum;
import com.jinsheng.dto.DtoSearch;
import com.jinsheng.vo.VoForumId;
import io.swagger.models.auth.In;

public interface ForumService {

    Result forumPage(Forum forum, Page page);

    Result forumSearch(DtoSearch search);

    Result addForum(Forum forum,String token);

    Result findAll();

    Result deleteForum(Integer forumId);

    Result updateForum(VoForumId forum);

    Result selectById(Integer id);

    Result updatePrivacy(Integer id);

    Result updatePopular(Integer id);

    Result findFormByUserId(Integer userId);

    Result forumDelById(Integer forumId);

    Result findPrivacyFormByUserId(Integer userId);
}
