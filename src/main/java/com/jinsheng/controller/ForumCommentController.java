package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.domain.ForumComment;
import com.jinsheng.service.ForumCommentService;
import com.jinsheng.utils.GraduationDesignConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "文章评论区接口")
@RestController
@RequestMapping("/forum-comments")
public class ForumCommentController {

    @Autowired
    private ForumCommentService fcService;


    @ApiOperation("根据文章ID查询评论")
    @GetMapping("/{forumId}")
    public Result forumCommentByForumId(@PathVariable Integer forumId){

        Result result = fcService.forumCommentByForumId(forumId);

        return result;
    }

    @ApiOperation("评论添加")
    @PostMapping("/add-comment")
    public Result addComment(@RequestBody ForumComment forumComment,
                             @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = fcService.addComment(forumComment,token);

        return result;
    }

    @ApiOperation("评论删除")
    @GetMapping("/delete-comment/{commentId}")
    public Result deleteComment(@PathVariable Integer commentId,
                             @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = fcService.deleteComment(commentId,token);

        return result;
    }

    @ApiOperation("根据文章ID查询评论数")
    @GetMapping("/forum-comment-Count/{forumId}")
    public Result forumCommentCountById(@PathVariable Integer forumId){

        Result result = fcService.forumCommentCountById(forumId);

        return result;
    }


}
