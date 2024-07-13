package com.jinsheng.service;

import com.jinsheng.common.Result;

public interface ForumLikeService {

    Result addForumLike(Integer forumId, String token);

    Result forumLikeCount(Integer forumId);

    Result forumLikeCurrentUser(String token);
}
