package com.jinsheng.controller;

import com.jinsheng.common.Code;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Video;
import com.jinsheng.dto.DtoSearch;
import com.jinsheng.dto.DtoVideo;
import com.jinsheng.service.VideoService;
import com.jinsheng.utils.GraduationDesignConstants;
import com.jinsheng.utils.VideoOrImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "视频接口")
@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoOrImageUtil videoUtil;

    /**
     * /video-upload
     * 视频上传
     * */
    @Value("${file.upload-video-path}") //配置文件中file.upload-path属性值注入
    private String uploadVideoPath;

    @ApiOperation("视频搜索")
    @PostMapping("/search")
    public Result videoSearch(@RequestBody DtoSearch search){

        Result result = videoService.videoSearch(search);

        return result;
    }

    @ApiOperation("视频分页")
    @PostMapping("/video-page")
    public Result videoPage(Video video, Page page){

        Result result = videoService.videoPage(video,page);

        return result;
    }

    @ApiOperation("视频添加")
    @PostMapping("/add-video")
    public Result addVideo(@RequestBody DtoVideo dtoVideo,
                           @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = videoService.addVideo(dtoVideo,token);

        return result;
    }

    @ApiOperation("视频上传")
    @PostMapping("/video-upload")
    public Result uploadImg(MultipartFile file) throws IOException { //MultipartFile file

        //拿到上传的图片的名字
        String filename = file.getOriginalFilename();

        // 获取文件扩展名
        String fileExtension = filename.substring(filename.lastIndexOf('.') + 1);

        try {

            // 根据文件扩展名进行不同路径存储
            switch (fileExtension.toLowerCase()) {
                case "mp4":
                case "webm":
                case "ogg":
                    //目标存储位置
                    videoUtil.videoOrImgUpload(file,uploadVideoPath);
                    break;
            }
            //上传成功
            return Result.ok("文件上传成功！");
        } catch (Exception e) {
            //上传失败
            return Result.err(Code.CODE_ERR_BUSINESS,"文件上传失败");
        }
    }

    @ApiOperation("视频文件删除")
    @GetMapping("/video-file-delete/{fileName}")
    public Result deleteImg(@PathVariable String fileName) throws IOException {

        Result result = videoUtil.videoOrImgDelete(fileName);

        return result;
    }

    @ApiOperation("视频删除")
    @DeleteMapping("/delete-video/{videoId}")
    public Result deleteVideo(@PathVariable Integer videoId) throws IOException {

        Result result = videoService.deleteVideo(videoId);

        return result;
    }

    @ApiOperation("视频是否可见")
    @GetMapping("/is-privacy/{videoId}")
    public Result videoIsPrivacy(@PathVariable Integer videoId){

        Result result = videoService.videoIsPrivacy(videoId);

        return result;
    }

    @ApiOperation("视频修改")
    @PostMapping("/update-video")
    public Result updateVideo(@RequestBody DtoVideo dtoVideo){

        Result result = videoService.updateVideo(dtoVideo);

        return result;
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/find-video-id/{videoId}")
    public Result findVideoById(@PathVariable Integer videoId){

        Result result = videoService.findVideoById(videoId);

        return result;
    }

    @ApiOperation("根据用户ID查询视频")
    @GetMapping("/video-userId/{userId}")
    public Result findVideoByUserId(@PathVariable Integer userId){

        Result result = videoService.findVideoByUserId(userId);

        return result;
    }

    @ApiOperation("根据用户ID查询隐私视频")
    @GetMapping("/privacy-video-userId/{userId}")
    public Result findPrivacyVideoByUserId(@PathVariable Integer userId){

        Result result = videoService.findPrivacyVideoByUserId(userId);

        return result;
    }


}
