package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.service.VideoLikeService;
import com.jinsheng.utils.GraduationDesignConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "视频点赞接口")
@RestController
@RequestMapping("/video-likes")
public class VideoLikeController {

    @Autowired
    private VideoLikeService videoLikeService;

    @ApiOperation("点赞")
    @GetMapping("/add-video-like/{videoId}")
    public Result addVideoLike(@PathVariable Integer videoId,
                               @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = videoLikeService.addVideoLike(videoId, token);

        return result;
    }

    @ApiOperation("当前用户点赞视频")
    @GetMapping("/video-like-current-user")
    public Result videoLikeCurrentUser(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = videoLikeService.videoLikeCurrentUser(token);

        return result;
    }

}
