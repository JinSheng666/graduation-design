package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.VideoCommentLike;
import com.jinsheng.mapper.VideoCommentLikeMapper;
import com.jinsheng.mapper.VideoCommentMapper;
import com.jinsheng.service.VideoCommentLikeService;
import com.jinsheng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class VideoCommentLikeServiceImpl implements VideoCommentLikeService {

    @Autowired
    private VideoCommentLikeMapper vclMapper;

    @Autowired
    private VideoCommentMapper vcMapper;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 评论点赞
     * */
    @Override
    public Result addVideoCommentLike(Integer commentId, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        //是否为空，状态 0 还是 1
        VideoCommentLike videoCommentLike = vclMapper.likeStatusById(userId, commentId);

        if (videoCommentLike == null){
            //为空则添加
            vclMapper.addVideoCommentLike(commentId, userId);
            //查询当前点赞数
            int count = vclMapper.countByCommentId(commentId);
            //拿到的点赞数给到文章tb_forum
            vcMapper.updateCommentLike(count,commentId);
            //
            return Result.result(Code.CODE_OK,true,"添加成功");
            //不为空且状态为0
        }else if (Integer.parseInt(videoCommentLike.getStatus()) == 0){
            //根据likeId修改状态
            vclMapper.updateVideoCommentLike(1,videoCommentLike.getVclId());
            //查询当前点赞数
            int count = vclMapper.countByCommentId(commentId);
            //拿到的点赞数给到文章tb_forum
            vcMapper.updateCommentLike(count,commentId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功1！");
            //不为空且状态为1
        }else if (Integer.parseInt(videoCommentLike.getStatus()) == 1){
            //根据likeId修改状态
            vclMapper.updateVideoCommentLike(0,videoCommentLike.getVclId());
            //查询当前点赞数
            int count = vclMapper.countByCommentId(commentId);
            //拿到的点赞数给到文章tb_forum
            vcMapper.updateCommentLike(count,commentId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功0！");
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"error");

    }
}
