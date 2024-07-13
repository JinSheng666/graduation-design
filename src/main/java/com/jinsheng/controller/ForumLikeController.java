package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.service.ForumLikeService;
import com.jinsheng.utils.GraduationDesignConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "论坛点赞接口")
@RestController
@RequestMapping("/forum-likes")
public class ForumLikeController {

    @Autowired
    private ForumLikeService flService;

    @ApiOperation("点赞")
    @GetMapping("/add-forum-like/{forumId}")
    public Result addForumLike(@PathVariable Integer forumId,
                               @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = flService.addForumLike(forumId, token);

        return result;
    }

    @ApiOperation("文章点赞数量")
    @GetMapping("/forum-like-count/{forumId}")
    public Result forumLikeCount(@PathVariable Integer forumId){

        Result result = flService.forumLikeCount(forumId);

        return result;
    }

    @ApiOperation("用户id查询点赞数据")
    @GetMapping("/forum-like-current-user")
    public Result forumLikeCurrentUser(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token ){

        Result result = flService.forumLikeCurrentUser(token);

        return result;
    }





}
