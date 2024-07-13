package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.domain.VideoComment;
import com.jinsheng.service.VideoCommentService;
import com.jinsheng.utils.GraduationDesignConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "视频评论接口")
@RestController
@RequestMapping("/video-comments")
public class VideoCommentController {

    @Autowired
    private VideoCommentService vcService;

    @ApiOperation("视频ID查询评论")
    @GetMapping("/{videoId}")
    public Result CommentByVideoId(@PathVariable Integer videoId){

        Result result = vcService.CommentByVideoId(videoId);

        return result;
    }

    @ApiOperation("评论添加")
    @PostMapping("/add-comment")
    public Result addComment(@RequestBody VideoComment videoComment,
                             @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = vcService.addComment(videoComment,token);

        return result;
    }

    @ApiOperation("评论删除")
    @PostMapping("/delete-comment/{commentId}")
    public Result deleteComment(@PathVariable Integer commentId,
                                @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = vcService.deleteComment(commentId,token);

        return result;
    }

    @ApiOperation("评论删除")
    @GetMapping ("/delete-comment-id/{commentId}")
    public Result deleteCommentById(@PathVariable Integer commentId){

        Result result = vcService.deleteCommentById(commentId);

        return result;
    }

}
