package com.jinsheng.service;

import com.jinsheng.common.Result;
import com.jinsheng.domain.VideoComment;

public interface VideoCommentService {

    Result CommentByVideoId(Integer videoId);

    Result addComment(VideoComment videoComment, String token);

    Result deleteComment(Integer commentId, String token);

    Result deleteCommentById(Integer commentId);
}
