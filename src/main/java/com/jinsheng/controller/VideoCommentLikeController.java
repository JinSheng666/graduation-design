package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.service.VideoCommentLikeService;
import com.jinsheng.utils.GraduationDesignConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "视频评论点赞接口")
@RestController
@RequestMapping("/video-comment-likes")
public class VideoCommentLikeController {

    @Autowired
    private VideoCommentLikeService vclService;

    @ApiOperation("点赞")
    @GetMapping("/{commentId}")
    public Result addForumCommentLike(@PathVariable Integer commentId,
                                      @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = vclService.addVideoCommentLike(commentId, token);

        return result;
    }

}
