package com.jinsheng.service;

import com.jinsheng.common.Result;

public interface VideoLikeService {
    Result addVideoLike(Integer videoId, String token);

    Result videoLikeCurrentUser(String token);
}
