package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.ForumCommentLike;
import com.jinsheng.mapper.ForumCommentLikeMapper;
import com.jinsheng.mapper.ForumCommentMapper;
import com.jinsheng.service.ForumCommentLikeService;
import com.jinsheng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ForumCommentLikeServiceImpl implements ForumCommentLikeService {

    @Autowired
    private ForumCommentLikeMapper fclMapper;

    @Autowired
    private ForumCommentMapper fcMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result addForumCommentLike(Integer commentId, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        //是否为空，状态 0 还是 1
        ForumCommentLike forumCommentLike = fclMapper.likeStatusById(userId, commentId);

        if (forumCommentLike == null){
            //为空则添加
            int i = fclMapper.addForumCommentLike(commentId, userId);
            //查询当前点赞数
            int count = fclMapper.countByCommentId(commentId);
            //拿到的点赞数给到文章tb_forum
            fcMapper.updateCommentLike(count,commentId);
            //
            return Result.result(Code.CODE_OK,true,"添加成功");
            //不为空且状态为0
        }else if (Integer.parseInt(forumCommentLike.getStatus()) == 0){
            //根据likeId修改状态
            fclMapper.updateForumCommentLike(1,forumCommentLike.getFclId());
            //查询当前点赞数
            int count = fclMapper.countByCommentId(commentId);
            //拿到的点赞数给到文章tb_forum
            fcMapper.updateCommentLike(count,commentId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功1！");
            //不为空且状态为1
        }else if (Integer.parseInt(forumCommentLike.getStatus()) == 1){
            //根据likeId修改状态
            fclMapper.updateForumCommentLike(0,forumCommentLike.getFclId());
            //查询当前点赞数
            int count = fclMapper.countByCommentId(commentId);
            //拿到的点赞数给到文章tb_forum
            fcMapper.updateCommentLike(count,commentId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功0！");
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"error");
    }

    /**
     * 评论点赞数
     * */
    @Override
    public Result commentLikeCount(Integer commentId) {

        int likeCount = fclMapper.countByCommentId(commentId);

        int code = likeCount >= 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        boolean flag = likeCount >= 0 ? true:false;
        String msg = likeCount >= 0 ? "success":"error";

        return Result.result(code,flag,msg,likeCount);
    }
}
