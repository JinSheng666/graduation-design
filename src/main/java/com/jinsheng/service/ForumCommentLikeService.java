package com.jinsheng.service;

import com.jinsheng.common.Result;

public interface ForumCommentLikeService {
    Result addForumCommentLike(Integer commentId, String token);

    Result commentLikeCount(Integer commentId);
}
