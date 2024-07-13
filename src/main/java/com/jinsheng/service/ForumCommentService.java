package com.jinsheng.service;

import com.jinsheng.common.Result;
import com.jinsheng.domain.ForumComment;

public interface ForumCommentService {

    Result forumCommentByForumId(Integer forumId);

    Result addComment(ForumComment forumComment,String token);

    Result deleteComment(Integer commentId,String token);

    Result forumCommentCountById(Integer forumId);
}
