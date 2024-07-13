package com.jinsheng.service;

import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Video;
import com.jinsheng.dto.DtoSearch;
import com.jinsheng.dto.DtoVideo;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface VideoService {

    Result videoSearch(DtoSearch search);

    Result videoPage(Video video, Page page);

    Result addVideo(DtoVideo dtoVideo, String token);

    Result deleteVideo(Integer videoId) throws IOException;

    Result videoIsPrivacy(Integer videoId);

    Result updateVideo(DtoVideo dtoVideo);

    Result findVideoById(Integer videoId);

    Result findVideoByUserId(Integer userId);

    Result findPrivacyVideoByUserId(Integer userId);
}
