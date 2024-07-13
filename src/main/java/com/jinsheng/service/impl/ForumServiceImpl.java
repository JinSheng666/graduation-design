package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Forum;
import com.jinsheng.domain.ForumComment;
import com.jinsheng.dto.DtoSearch;
import com.jinsheng.mapper.*;
import com.jinsheng.service.ForumService;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.vo.VoForum;
import com.jinsheng.vo.VoForumId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
@Service
@Slf4j
@CacheConfig(cacheNames = "com.jinsheng.service.impl.ForumServiceImpl")
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PopularMapper popularMapper;

    @Autowired
    private ForumCommentMapper fcMapper;

    @Autowired
    private ForumLikeMapper flMapper;

    @Autowired
    private ForumCommentLikeMapper fclMapper;


    /**
     * /img-upload
     * 图片上传
     * 参数MultipartFile file对象封装了上传的图片;
     * */
    @Value("${file.upload-img-path}") //配置文件中file.upload-path属性值注入
    private String uploadImgPath;

    @Value("${http.access-img-path}") //配置文件中file.upload-path属性值注入
    private String httpUploadImgPath;

    /**
     * /video-upload
     * 视频上传
     * */
    @Value("${file.upload-video-path}") //配置文件中file.upload-path属性值注入
    private String uploadVideoPath;

    @Value("${http.access-video-path}") //配置文件中file.upload-path属性值注入
    private String httpUploadVideoPath;

    /**
     * 论坛分页
     * */
    @Override
    public Result forumPage(Forum forum, Page page) {

        int count = forumMapper.forumCount(forum);
        page.setTotalNum(count);

        List<Forum> forums = forumMapper.forumPage(forum, page);
        page.setResultList(forums);

        int code = !forums.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !forums.isEmpty() ? true:false;
        String msg = !forums.isEmpty() ?"论坛分页查询成功！":"论坛分页查询失败！";

        return Result.page(code,flag,msg,forums,page.getTotalNum());
    }

    /**
     * 论坛搜索
     * */
    @Override
    public Result forumSearch(DtoSearch search) {

        Page page = new Page();

        page.setPageNum(search.getPageNum());
        page.setPageSize(search.getPageSize());

        int count = forumMapper.forumSearchCount(search.getSearch());
        page.setTotalNum(count);

        List<VoForum> voForums = forumMapper.forumSearch(search.getSearch(), page);

        for (VoForum forum: voForums) {

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

        int code = !voForums.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !voForums.isEmpty() ? true:false;
        String msg = !voForums.isEmpty() ?"查询论坛成功！":"查询论坛失败！";

        return Result.page(code,flag,msg,voForums,page.getTotalNum());
    }

    /**
     * 论坛添加
     * */
    @Override
    public Result addForum(Forum forum,String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        forum.setUserId(currentUser.getUserId());

        /*图片*/
        List<String> picss = forum.getPicss();

        /*拿出集合里的数据遍历进行字符串拼接*/
        for (int i = 0; i < picss.size(); i++) {
            String httpImgPath = httpUploadImgPath + picss.get(i);
            log.debug("httpImgPath:"+httpImgPath);
            picss.set(i, httpImgPath); //将此列表中指定位置的元素替换为指定的元素。
        }
        /* 字符串拼接 */
        String pics = StringUtils.join(picss, ",");

        if (pics == null || pics == "" || pics == " "){
            forum.setPics(null);
        }else {
            /*图片赋值*/
            forum.setPics(pics);
        }

        /*视频*/
        List<String> videos = forum.getVideos();

        /*拿出集合里的数据遍历进行字符串拼接*/
        for (int a = 0; a < videos.size(); a++) {
            String httpVideoPath = httpUploadVideoPath + videos.get(a);
            log.debug("httpImgPath:" + httpVideoPath);
            videos.set(a, httpVideoPath); //将此列表中指定位置的元素替换为指定的元素。
        }
        log.debug("videos:"+videos);
        //拼接字符串
        String video = StringUtils.join(videos, ",");
        //赋值

        if (video == null || video == "" || video == " "){
            forum.setVideoUrl(null);
        }else {
            forum.setVideoUrl(video);
        }

        int i = forumMapper.addForum(forum);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"文章添加成功！":"文章添加失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 查询所有
     * */
    @Override
    public Result findAll() {

        List<Forum> all = forumMapper.findAll();

        int code = !all.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !all.isEmpty() ? true:false;
        String msg = !all.isEmpty() ?"文章查询成功！":"文章查询失败！";

        return Result.result(code,flag,msg,all);
    }

    /**
     * 文章删除
     * */
    @Override
    public Result deleteForum(Integer forumId) {

        int i = forumMapper.deleteForum(forumId);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"文章删除成功！":"文章删除失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 文章修改
     * */
    @Override
    public Result updateForum(VoForumId forum) {
        /*图片*/
        List<String> picss = forum.getPicss();
        /*视频*/
        List<String> videos = forum.getVideos();

        if (!picss.isEmpty() && picss != null || !videos.isEmpty() && picss != null){
            /*拿出集合里的数据遍历进行字符串拼接*/
            for (int i = 0; i < picss.size(); i++) {
                String httpImgPath = httpUploadImgPath + picss.get(i);
                picss.set(i, httpImgPath); //将此列表中指定位置的元素替换为指定的元素。
            }
            /* 字符串拼接 */
            String pics = StringUtils.join(picss, ",");
            log.debug(pics);
            /*图片赋值*/
            forum.setPics(pics);

            /*拿出集合里的数据遍历进行字符串拼接*/
            for (int a = 0; a < videos.size(); a++) {
                String httpVideoPath = httpUploadVideoPath + videos.get(a);
                videos.set(a, httpVideoPath); //将此列表中指定位置的元素替换为指定的元素。
            }
            //拼接字符串
            String video = StringUtils.join(videos, ",");
            //赋值
            forum.setVideoUrl(video);

        }

        int i = forumMapper.updateForum(forum);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"文章修改成功！":"文章修改失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据ID查询数据
     * */
    @Override
    public Result selectById(Integer id) {

        VoForumId voForumId = forumMapper.selectById(id);

        String pics = voForumId.getPics();
        String videoUrl = voForumId.getVideoUrl();

        if (pics == null){
            String[] array = new String[0];
            voForumId.setPicss(Arrays.asList(array));
        }else {
            voForumId.setPicss(Arrays.asList(pics.split(",")));
        }
        if (videoUrl == null){
            String[] array = new String[0];
            voForumId.setVideos(Arrays.asList(array));
        }else {
            voForumId.setVideos(Arrays.asList(videoUrl.split(",")));
        }

        int code = voForumId != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        boolean flag = voForumId != null ? true:false;
        String msg = voForumId != null ?"文章查询成功！":"文章查询失败！";

        return Result.result(code,flag,msg,voForumId);
    }

    /**
     * 隐私修改
     * */
    @Override
    public Result updatePrivacy(Integer id) {

        int i = forumMapper.updatePrivacy(id);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"success！":"error!";

        return Result.result(code,flag,msg);
    }

    /**
     * 是否热门
     * */
    @Override
    public Result updatePopular(Integer id) {

        int a = forumMapper.updatePopular(id);
        //满足条件在热门榜里添加数据
        if (a > 0){
            popularMapper.addPopular(id);
        }

        int code = a > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = a > 0 ? true:false;
        String msg = a > 0 ?"success！":"error!";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据用户ID查询文章
     * */
    @Override
    public Result findFormByUserId(Integer userId) {

        List<VoForum> formByUserId = forumMapper.findFormByUserId(userId);

        int count = forumMapper.findFormCountByUserId(userId);

        for (VoForum forum: formByUserId) {

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

        int code = !formByUserId.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !formByUserId.isEmpty() ? true:false;
        String msg = !formByUserId.isEmpty() ?"文章查询成功！":"文章查询失败！";

        return Result.page(code,flag,msg,formByUserId,count);
    }

    /**
     *
     * 根据id删除文章
     * */
    @Override
    public Result forumDelById(Integer forumId) {
        //删除点赞
        int likeDel = flMapper.likeDelByForumId(forumId);

        if (likeDel < 0){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"文章点赞删除失败！");
        }

        //获得评论id
        List<Integer> commentByForumId = fcMapper.getCommentByForumId(forumId);

        if (!commentByForumId.isEmpty()){
            //删除评论点赞
            int commentLikesDel = fclMapper.commentLikeDelByCommentId(commentByForumId);

            if (commentLikesDel < 0){
                return Result.result(Code.CODE_ERR_BUSINESS,false,"文章评论点赞删除失败！");
            }

            int commentDelete = fcMapper.commentDelete(forumId);

            if (commentDelete < 0){
                return Result.result(Code.CODE_ERR_BUSINESS,false,"文章评论删除失败！");
            }
        }

        int popularDelByForumId = popularMapper.popularDelByForumId(forumId);

        if (popularDelByForumId < 0){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"文章热门删除失败！");
        }

        int i = forumMapper.forumDelById(forumId);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"文章删除成功！":"文章删除失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 找到隐私文章
     * */
    @Override
    public Result findPrivacyFormByUserId(Integer userId) {

        List<VoForum> formByUserId = forumMapper.findPrivacyFormByUserId(userId);

        int count = forumMapper.findPrivacyFormCountByUserId(userId);

        for (VoForum forum: formByUserId) {

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

        int code = !formByUserId.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !formByUserId.isEmpty() ? true:false;
        String msg = !formByUserId.isEmpty() ?"文章查询成功！":"文章查询失败！";

        return Result.page(code,flag,msg,formByUserId,count);
    }


}
