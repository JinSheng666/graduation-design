package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Forum;
import com.jinsheng.domain.ForumComment;
import com.jinsheng.mapper.ForumCommentMapper;
import com.jinsheng.mapper.ForumMapper;
import com.jinsheng.service.ForumCommentService;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.vo.VoForumComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ForumCommentServiceImpl implements ForumCommentService {

    @Autowired
    private ForumCommentMapper fcMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ForumMapper forumMapper;

    //将查询到的数据根据父子关系通过递归算法获得数据的层级表现
    private List<VoForumComment> CommentList(List<VoForumComment> commentList, int pid){

        //拿到所有的一级评论
        List<VoForumComment> firstLevelCommentList = new ArrayList<>();
        for (VoForumComment comment: commentList) {
            //满足ParentId等于pid的条件就得到一级评论并添加到firstLevelCommentList集合中去
            if (comment.getParentId() == pid){
                firstLevelCommentList.add(comment);
            }
        }

        //拿到每个一级的二级评论
        for (VoForumComment firstComment : firstLevelCommentList) {
            //根据拿到的一级评论里的文章id再去拿到每一个一级的二级评论
            List<VoForumComment> secondLevelAuthList = CommentList(commentList, firstComment.getFcId());
            //数据注入到voForumComment中自定义的二级菜单ChildComment集合中
            firstComment.setChildComment(secondLevelAuthList);
        }

        return firstLevelCommentList;
    }

    /**
     * 根据文章ID查询所属评论
     * */
    @Override
    public Result forumCommentByForumId(Integer forumId) {

        int count = fcMapper.forumCommentCount(forumId);

        List<VoForumComment> forumComments = fcMapper.forumCommentByForumId(forumId);
        //递归获得文章父 子评论
        List<VoForumComment> voForumComments = CommentList(forumComments, 0);

        int code = !forumComments.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !forumComments.isEmpty() ? true:false;
        String msg = !forumComments.isEmpty() ?"success":"error";

        return Result.page(code,flag,msg,voForumComments,count);
    }

    /**
     * 评论添加
     * */
    @Override
    public Result addComment(ForumComment forumComment,String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        forumComment.setUserId(currentUser.getUserId());

        Integer parentId = forumComment.getParentId();

        if (parentId == null){
            forumComment.setParentId(0);
        }

        int i = fcMapper.addComment(forumComment);

        if (i > 0){
            //评论添加成功查询评论总数
            int count = fcMapper.forumCommentCount(forumComment.getForumId());
            log.debug("forumCommentCount:"+count);
            //拿到的评论数量更新到论坛中
            forumMapper.updateComment(count,forumComment.getForumId());
        }

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"success":"error";

        return Result.result(code,flag,msg);
    }

    /**
     * 评论删除
     * 需满足任一条件才可执行删除
     * 1、userID = comment.userId
     * 2、userId = forum.userId
     * */
    @Override
    public Result deleteComment(Integer commentId, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();

        //条件
        //获得forum_id
        int forumId = fcMapper.findCommentById(commentId);
        ForumComment forumComment = fcMapper.findCommentByUserId(userId,commentId);
        Forum forum = forumMapper.findForumByUserId(userId,forumId);
        if (forumComment != null || forum != null){
            //根据ID删除评论
            int i = fcMapper.deleteComment(commentId);

            //查询子评论
            List<ForumComment> parentId = fcMapper.findParentId(commentId);
            //不为空则删除
            if (parentId.isEmpty()){
                fcMapper.deleteCommentParent(commentId);
            }

            //以上条件成立，查询文章当前评论数
            if (i>0 && forumId > 0){
                int count = fcMapper.forumCommentCount(forumId);
                forumMapper.updateComment(count,forumId);
            }

            int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
            Boolean flag = i>0 ? true:false;
            String msg = i>0 ?"success":"error";

            return Result.result(code,flag,msg);
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"error");

    }

    /**
     * 获取评论数
     * */
    @Override
    public Result forumCommentCountById(Integer forumId) {

        int count = fcMapper.forumCommentCountById(forumId);

        int code = count >=0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = count >=0 ? true:false;
        String msg = count >=0 ?"success":"error";

        return Result.result(code,flag,msg,count);
    }
}
