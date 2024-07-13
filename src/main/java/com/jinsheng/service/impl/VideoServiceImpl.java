package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Video;
import com.jinsheng.dto.DtoSearch;
import com.jinsheng.dto.DtoVideo;
import com.jinsheng.mapper.VideoCommentLikeMapper;
import com.jinsheng.mapper.VideoCommentMapper;
import com.jinsheng.mapper.VideoLikeMapper;
import com.jinsheng.mapper.VideoMapper;
import com.jinsheng.service.VideoService;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.utils.VideoOrImageUtil;
import com.jinsheng.vo.VoForum;
import com.jinsheng.vo.VoVideo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private VideoOrImageUtil videoOrImageUtil;

    @Autowired
    private VideoLikeMapper vlMapper;

    @Autowired
    private VideoCommentMapper vcMapper;

    @Autowired
    private VideoCommentLikeMapper vclMapper;

    /**
     * /video-upload
     * 视频上传
     * */
    @Value("${file.upload-video-path}") //配置文件中file.upload-path属性值注入
    private String uploadVideoPath;

    @Value("${http.access-video-path}") //配置文件中file.upload-path属性值注入
    private String httpUploadVideoPath;

    /**
     * 视频搜索
     * */
    @Override
    public Result videoSearch(DtoSearch search) {

        Page page = new Page();

        page.setPageNum(search.getPageNum());
        page.setPageSize(search.getPageSize());

        int count = videoMapper.videoSearchCount(search.getSearch());
        page.setTotalNum(count);

        List<VoVideo> voVideos = videoMapper.videoSearch(search.getSearch(), page);

        for (VoVideo video: voVideos) {

            String videoUrl = video.getVideo();

            if (videoUrl == null){
                String[] array = new String[0];
                video.setVideos(array);
            }else {
                String[] split = videoUrl.split(",");
                video.setVideos(split);
                video.setVideo(split[0]);
            }

        }

        int code = !voVideos.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !voVideos.isEmpty() ? true:false;
        String msg = !voVideos.isEmpty() ?"视频搜索成功！":"视频搜索失败！";

        return Result.page(code,flag,msg,voVideos,page.getTotalNum());
    }

    /**
     * 视频分页
     * */
    @Override
    public Result videoPage(Video video, Page page) {

        int count = videoMapper.videoPageCount(video);
        page.setTotalNum(count);

        List<Video> videos = videoMapper.videoPage(video, page);
        page.setResultList(videos);

        int code = !videos.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !videos.isEmpty() ? true:false;
        String msg = !videos.isEmpty() ?"视频查询成功！":"视频查询失败！";

        return Result.page(code,flag,msg,page.getResultList(),page.getTotalNum());
    }

    /**
     * 视频添加
     * */
    @Override
    public Result addVideo(DtoVideo dtoVideo, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        dtoVideo.setUserId(userId);
        //视频
        List<String> videos = dtoVideo.getVideos();

        for (int a = 0; a < videos.size(); a++) {
            String httpVideoPath = httpUploadVideoPath + videos.get(a);
            log.debug("httpImgPath:" + httpVideoPath);
            videos.set(a, httpVideoPath); //将此列表中指定位置的元素替换为指定的元素。
        }
        log.debug("videos:"+videos);
        //拼接字符串
        String video = StringUtils.join(videos, ",");
        dtoVideo.setVideo(video);

        int i = videoMapper.addVideo(dtoVideo);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"视频添加成功！":"视频添加失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据视频ID删除
     * */
    @Override
    public Result deleteVideo(Integer videoId) throws IOException {

        //删除视频点赞
        int like = vlMapper.delLikeVideoId(videoId);
        if (like < 0){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"点赞删除失败！");
        }

        //评论点赞删除
        List<Integer> commentIds = vcMapper.getCommentIdsByVideoId(videoId);
        if (!commentIds.isEmpty()){
            vclMapper.delCommentLikeByCommentId(commentIds);
        }

        //评论删除
        int comment = vcMapper.delCommentByVideoId(videoId);
        if(comment < 0){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"评论删除失败！");
        }

        String videoUrl = videoMapper.findVideoById(videoId);

        int i = videoMapper.deleteVideo(videoId);
        //删除失败
        if (i < 0){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"error");
        }
        Result result = null;
        //文件删除
        if (videoUrl != null){
            // 多张图片则使用逗号分割字符串
            String[] urls = videoUrl.split(",");
            // 遍历每个URL
            for (String url : urls) {
                // 使用斜杠分割URL，获取文件名部分
                String[] parts = url.split("/");
                //获取文件名部分
                String fileName = parts[parts.length - 1];
                // 输出文件名
                log.debug("File Name: " + fileName);
                //文件删除
                result = videoOrImageUtil.videoOrImgDelete(fileName);
            }
        }

        return result;
    }

    /**
     * 视频是否可见
     * */
    @Override
    public Result videoIsPrivacy(Integer videoId) {
        //查询是否隐私
        int IsPrivacy = videoMapper.IsPrivacy(videoId);

        int code = 0;
        String msg = null;
        Boolean flag = null;

        if (IsPrivacy == 0){
            int i = videoMapper.videoIsPrivacy(1,videoId);
            msg = i > 0 ?"视频隐私设置成功！":"视频隐私设置失败！";
            code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
            flag = i > 0 ? true:false;

        }else if (IsPrivacy == 1){
            int i = videoMapper.videoIsPrivacy(0,videoId);
            msg = i > 0 ?"视频可见设置成功！":"视频可见设置失败！";
            code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
            flag = i > 0 ? true:false;;

        }

        return Result.result(code,flag,msg);
    }

    /**
     * 视频修改
     * */
    @Override
    public Result updateVideo(DtoVideo dtoVideo) {
        //视频
        List<String> videos = dtoVideo.getVideos();
        //如果修改时视频跟着修改，就把原视频删除
        /*if (!videos.isEmpty()){
            //
            DtoVideo video1 = videoMapper.idFindVideo(dtoVideo.getVideoId());
            //拿到视频名字
            String videoUrl = video1.getVideo();
            // 多张图片则使用逗号分割字符串
            String[] urls = videoUrl.split(",");
            // 遍历每个URL
            for (String url : urls) {
                // 使用斜杠分割URL，获取文件名部分
                String[] parts = url.split("/");
                //获取文件名部分
                String fileName = parts[parts.length - 1];
                // 输出文件名
                log.debug("File Name: " + fileName);
                //文件删除
                deleteFileName(fileName);
         }
        }*/

        //遍历视频url
        for (int a = 0; a < videos.size(); a++) {
            String httpVideoPath = httpUploadVideoPath + videos.get(a);
            log.debug("httpImgPath:" + httpVideoPath);
            videos.set(a, httpVideoPath); //将此列表中指定位置的元素替换为指定的元素。
        }
        log.debug("videos:"+videos);
        //拼接字符串
        String video = StringUtils.join(videos, ",");
        dtoVideo.setVideo(video);

        int i = videoMapper.updateVideo(dtoVideo);

        int code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i > 0 ? true:false;
        String msg = i > 0 ?"视频修改成功！":"视频修改失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据ID查询
     * */
//    @Cacheable(key = "'video:all'")
//    @CacheEvict(value="video", beforeInvocation=true)
    @Override
    public Result findVideoById(Integer videoId) {

        DtoVideo dtoVideo = videoMapper.idFindVideo(videoId);

            String videoUrl = dtoVideo.getVideo();

            if (videoUrl == null){
                String[] array = new String[0];
                dtoVideo.setVideos(Arrays.asList(array));
            }else {
                String[] split = videoUrl.split(",");
                dtoVideo.setVideos(Arrays.asList(split));
                dtoVideo.setVideo(split[0]);
            }


        int code = dtoVideo != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = dtoVideo != null ? true:false;
        String msg = dtoVideo != null ?"视频查询成功！":"视频查询失败！";

        return Result.result(code,flag,msg,dtoVideo);
    }

    /**
     * 根据用户ID查询视频
     * */
    @Override
    public Result findVideoByUserId(Integer userId) {

        List<VoVideo> videoByUserId = videoMapper.findVideoByUserId(userId);

        for (VoVideo video: videoByUserId) {

            String videoUrl = video.getVideo();

            if (videoUrl == null){
                String[] array = new String[0];
                video.setVideos(array);
            }else {
                String[] split = videoUrl.split(",");
                video.setVideos(split);
                video.setVideo(split[0]);

            }

        }

        int count = videoMapper.findVideoCountByUserId(userId);

        int code = !videoByUserId.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !videoByUserId.isEmpty() ? true:false;
        String msg = !videoByUserId.isEmpty() ?"视频查询成功！":"视频查询失败！";

        return Result.page(code,flag,msg,videoByUserId,count);
    }

    /**
     * 查询不可见视频
     * */
    @Override
    public Result findPrivacyVideoByUserId(Integer userId) {

        List<VoVideo> videoByUserId = videoMapper.findPrivacyVideoByUserId(userId);

        for (VoVideo video: videoByUserId) {

            String videoUrl = video.getVideo();

            if (videoUrl == null){
                String[] array = new String[0];
                video.setVideos(array);
            }else {
                String[] split = videoUrl.split(",");
                video.setVideos(split);
                video.setVideo(split[0]);

            }

        }

        int count = videoMapper.findVideoCountByUserId(userId);

        int code = !videoByUserId.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !videoByUserId.isEmpty() ? true:false;
        String msg = !videoByUserId.isEmpty() ?"视频查询成功！":"视频查询失败！";

        return Result.page(code,flag,msg,videoByUserId,count);
    }
}
