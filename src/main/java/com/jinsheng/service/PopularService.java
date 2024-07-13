package com.jinsheng.service;

import com.jinsheng.common.Result;

public interface PopularService {

    Result findPopular();

    Result popularAll();

    Result deletePopularById(Integer popularId);

    Result popularById(Integer popularId);
}
