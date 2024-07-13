package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.ForumLike;
import com.jinsheng.mapper.ForumLikeMapper;
import com.jinsheng.mapper.ForumMapper;
import com.jinsheng.service.ForumLikeService;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.vo.VoForum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ForumLikeServiceImpl implements ForumLikeService {

    @Autowired
    private ForumLikeMapper flMapper;

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 论坛点赞
     * */
    @Override
    public Result addForumLike(Integer forumId, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        //是否为空，状态 0 还是 1
        ForumLike forumLike = flMapper.likeStatusById(userId, forumId);

        if (forumLike == null){
            //为空则添加
            flMapper.addForumLike(forumId, userId);
            //查询当前点赞数
            int count = flMapper.countByForumId(forumId);
            //拿到的点赞数给到文章tb_forum
            forumMapper.updateLike(count,forumId);
            //
            return Result.result(Code.CODE_OK,true,"添加成功");
            //不为空且状态为0
        }else if (Integer.parseInt(forumLike.getStatus()) == 0){
            //根据likeId修改状态
            flMapper.updateForumLike(1,forumLike.getFlId());
            //查询当前点赞数
            int count = flMapper.countByForumId(forumId);
            //拿到的点赞数给到文章tb_forum
            forumMapper.updateLike(count,forumId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功1！");
            //不为空且状态为1
        }else if (Integer.parseInt(forumLike.getStatus()) == 1){
            //根据likeId修改状态
            flMapper.updateForumLike(0,forumLike.getFlId());
            //查询当前点赞数
            int count = flMapper.countByForumId(forumId);
            //拿到的点赞数给到文章tb_forum
            forumMapper.updateLike(count,forumId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功0！");
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"error");
    }

    /**
     * 文章点赞计数查询
     * */
    @Override
    public Result forumLikeCount(Integer forumId) {

        int forumLikeCount = flMapper.countByForumId(forumId);

        int code = forumLikeCount >= 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        boolean flag = forumLikeCount >= 0 ? true:false;
        String msg = forumLikeCount >= 0 ? "success":"error";

        return Result.result(code,flag,msg,forumLikeCount);
    }

    /**
     * 获取当前用户喜欢的文章
     * */
    @Override
    public Result forumLikeCurrentUser(String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        Integer userId = currentUser.getUserId();

        List<Integer> forumIds = flMapper.forumLikeCurrentUser(userId);

        List<Integer> userIds = forumMapper.getForumUserIds(forumIds);

        List<VoForum> forums = forumMapper.getForumByIds(forumIds,userIds);

        for (VoForum forum: forums) {

            String pics = forum.getPics();
            String videoUrl = forum.getVideoUrl();

            if (pics == null){
                String[] array = new String[0];
                forum.setPicss(array);
            }else {
                forum.setPicss(pics.split(","));
            }
            if (videoUrl == null){
                String[] array = new String[0];
                forum.setVideoUrls(array);
            }else {
                forum.setVideoUrls(videoUrl.split(","));
            }

        }

        int code = !forums.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        boolean flag = !forums.isEmpty() ? true:false;
        String msg = !forums.isEmpty() ? "success":"error";

        return Result.result(code,flag,msg,forums);
    }
}
