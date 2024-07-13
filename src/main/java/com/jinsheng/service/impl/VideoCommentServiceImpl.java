package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.VideoComment;
import com.jinsheng.mapper.VideoCommentMapper;
import com.jinsheng.mapper.VideoMapper;
import com.jinsheng.service.VideoCommentService;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.vo.VoVideoComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
public class VideoCommentServiceImpl implements VideoCommentService {

    @Autowired
    private VideoCommentMapper vcMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private TokenUtils tokenUtils;

    //将查询到的数据根据父子关系通过递归算法获得数据的层级表现
    private List<VoVideoComment> CommentList(List<VoVideoComment> commentList, int pid){

        //拿到所有的一级评论
        List<VoVideoComment> firstLevelCommentList = new ArrayList<>();
        for (VoVideoComment comment: commentList) {
            //满足ParentId等于pid的条件就得到一级评论并添加到firstLevelCommentList集合中去
            if (comment.getParentId() == pid){
                firstLevelCommentList.add(comment);
            }
        }

        //拿到每个一级的二级评论
        for (VoVideoComment firstComment : firstLevelCommentList) {
            //根据拿到的一级评论里的文章id再去拿到每一个一级的二级评论
            List<VoVideoComment> secondLevelAuthList = CommentList(commentList, firstComment.getVcId());
            //数据注入到voForumComment中自定义的二级菜单ChildComment集合中
            firstComment.setChildComment(secondLevelAuthList);
        }

        return firstLevelCommentList;
    }

    /**
     * 查询视频评论
     * */
    @Override
    public Result CommentByVideoId(Integer videoId) {
        //
        int count = vcMapper.CommentCountByVideoId(videoId);
        //拿到数据
        List<VoVideoComment> videoComments = vcMapper.CommentByVideoId(videoId);
        //递归获得父子数据
        List<VoVideoComment> voVideoComments = CommentList(videoComments, 0);

        log.debug("视频评论："+String.valueOf(voVideoComments));

        int code = !videoComments.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !videoComments.isEmpty() ? true:false;
        String msg = !videoComments.isEmpty() ?"success":"error";

        return Result.page(code,flag,msg,voVideoComments,count);
    }

    /**
     * 评论添加
     * */
    @Override
    public Result addComment(VideoComment videoComment, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //拿到用户ID
        Integer userId = currentUser.getUserId();
        //
        videoComment.setUserId(userId);
        //评论是否为空
        if (videoComment.getParentId() == null){
            videoComment.setParentId(0);
        }
        //以上条件满足添加评论
        int i = vcMapper.addComment(videoComment);
        //
        if (i > 0){
            //评论添加成功查询评论总数
            int count = vcMapper.CommentCountByVideoId(videoComment.getVideoId());
            //拿到的评论数量更新到论坛中
            videoMapper.updateCommentCount(count,videoComment.getVideoId());
        }

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"success":"error";

        return Result.result(code,flag,msg);
    }

    /**
     * 评论删除
     * */
    @Override
    public Result deleteComment(Integer commentId, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        //根据评论ID查询
        VideoComment videoComment = vcMapper.findCommentByVCId(commentId);
        //执行删除
        int i = vcMapper.deleteComment(commentId, userId);
        //删除成功
        if (i > 0){
            //评论删除成功查询评论总数
            int count = vcMapper.CommentCountByVideoId(videoComment.getVideoId());
            //拿到的评论数量更新到论坛中
            videoMapper.updateCommentCount(count,videoComment.getVideoId());
        }

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"success":"error";

        return Result.result(code,flag,msg);
    }

    @Override
    public Result deleteCommentById(Integer commentId) {

        int parent = vcMapper.deleteParentByCommentId(commentId);

        //根据评论ID查询
        VideoComment videoComment = vcMapper.findCommentByVCId(commentId);
        int i = vcMapper.deleteCommentById(commentId);
        //删除成功
        if (i > 0){
            //评论删除成功查询评论总数
            int count = vcMapper.CommentCountByVideoId(videoComment.getVideoId());
            //拿到的评论数量更新到论坛中
            videoMapper.updateCommentCount(count,videoComment.getVideoId());
        }


        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"success":"error";

        return Result.result(code,flag,msg);
    }
}
