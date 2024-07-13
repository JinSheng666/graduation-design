package com.jinsheng.service;

import com.jinsheng.common.Result;

public interface VideoCommentLikeService {

    Result addVideoCommentLike(Integer commentId, String token);
}
