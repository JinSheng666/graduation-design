package com.jinsheng.controller;


import com.jinsheng.common.Result;
import com.jinsheng.service.ForumCommentLikeService;
import com.jinsheng.utils.GraduationDesignConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "文章评论点赞接口")
@RestController
@RequestMapping("/forum-comment-likes")
public class ForumCommentLikeController {

    @Autowired
    private ForumCommentLikeService fclService;

    @ApiOperation("点赞")
    @GetMapping("/{commentId}")
    public Result addForumCommentLike(@PathVariable Integer commentId,
                               @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = fclService.addForumCommentLike(commentId, token);

        return result;
    }

    @ApiOperation("评论点赞数")
    @GetMapping("/comment-like-count/{commentId}")
    public Result commentLikeCount(@PathVariable Integer commentId){

        Result result = fclService.commentLikeCount(commentId);

        return result;
    }


}
