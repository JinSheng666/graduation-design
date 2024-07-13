package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.VideoLike;
import com.jinsheng.mapper.VideoLikeMapper;
import com.jinsheng.mapper.VideoMapper;
import com.jinsheng.service.VideoLikeService;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.vo.VoVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
@CacheConfig(cacheNames = "com.jinsheng.service.impl.VideoLikeServiceImpl")
public class VideoLikeServiceImpl implements VideoLikeService {

    @Autowired
    private VideoLikeMapper videoLikeMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private VideoMapper videoMapper;

    /**
     * 视频点赞
     * */
    /*@Cacheable(key = "'videoLike:like'")
    @CacheEvict()*/
    @Override
    public Result addVideoLike(Integer videoId, String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();
        //是否为空，状态 0 还是 1
        VideoLike videoLike = videoLikeMapper.likeStatusById(userId, videoId);

        if (videoLike == null){
            //为空则添加
            videoLikeMapper.addVideoLike(userId,videoId);
            //查询当前点赞数
            int count = videoLikeMapper.countByVideoId(videoId);
            //拿到的点赞数给到文章tb_forum
            videoMapper.updateLikeCount(count,videoId);
            //
            return Result.result(Code.CODE_OK,true,"添加成功");
            //不为空且状态为0
        }else if (Integer.parseInt(videoLike.getStatus()) == 0){
            //根据likeId修改状态
            videoLikeMapper.updateVideoLike(1,videoLike.getVlId());
            //查询当前点赞数
            int count = videoLikeMapper.countByVideoId(videoId);
            //拿到的点赞数给到文章tb_forum
            videoMapper.updateLikeCount(count,videoId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功1！");
            //不为空且状态为1
        }else if (Integer.parseInt(videoLike.getStatus()) == 1){
            //根据likeId修改状态
            videoLikeMapper.updateVideoLike(0,videoLike.getVlId());
            //查询当前点赞数
            int count = videoLikeMapper.countByVideoId(videoId);
            //拿到的点赞数给到文章tb_forum
            videoMapper.updateLikeCount(count,videoId);
            //
            return Result.result(Code.CODE_OK,true,"修改成功0！");
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"error");

    }

    /**
     * 获得当前用户点赞的视频数据
     * */
    @Override
    public Result videoLikeCurrentUser(String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        Integer userId = currentUser.getUserId();

        List<Integer> videoIds = videoLikeMapper.videoLikeCurrentUser(userId);

        List<Integer> userIds = videoMapper.getVideoUserIds(videoIds);

        List<VoVideo> videos = videoMapper.getVideoByIds(videoIds,userIds);

        for (VoVideo video: videos) {

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

        int code = !videos.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        String msg = !videos.isEmpty() ? "success":"error";
        Boolean flag = !videos.isEmpty() ? true:false;

        return Result.result(code,flag,msg,videos);
    }
}
