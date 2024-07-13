package com.jinsheng.controller;

import com.jinsheng.common.Code;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Forum;
import com.jinsheng.dto.DtoSearch;
import com.jinsheng.service.ForumService;
import com.jinsheng.utils.GraduationDesignConstants;
import com.jinsheng.utils.VideoOrImageUtil;
import com.jinsheng.vo.VoForumId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = "论坛接口")
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private VideoOrImageUtil videoUtil;

    /**
     * /img-upload
     * 图片上传
     * 参数MultipartFile file对象封装了上传的图片;
     * */
    @Value("${file.upload-img-path}") //配置文件中file.upload-path属性值注入
    private String uploadImgPath;

    /**
     * /video-upload
     * 视频上传
     * */
    @Value("${file.upload-video-path}") //配置文件中file.upload-path属性值注入
    private String uploadVideoPath;

    @ApiOperation("论坛分页")
    @PostMapping("/forum-page")
    public Result forumPage(Forum forum, Page page){

        page.setPageNum(1);
        page.setPageSize(5);

        Result result = forumService.forumPage(forum, page);

        return result;
    }

    @ApiOperation("论坛搜索")
    @PostMapping("/search")
    public Result forumSearch(@RequestBody DtoSearch search){

        System.out.println(search.getPageNum());
        System.out.println(search.getPageSize());

        Result result = forumService.forumSearch(search);

        return result;
    }

    @ApiOperation("查询所有")
    @GetMapping("/findAll")
    public Result findAll(){

        Result result = forumService.findAll();

        return result;
    }

    @ApiOperation("论坛添加")
    @PostMapping("/add-forum")
    public Result addForum(@RequestBody Forum forum,
                           @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = forumService.addForum(forum,token);

        return result;
    }

    @ApiOperation("图片或视频上传")
    @PostMapping("/img-video-upload")
    public Result uploadImg(MultipartFile file){ //MultipartFile file

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
                case "png":
                case "jpg":
                case "jpeg":
                case "gif":
                    //目标存储位置
                    videoUtil.videoOrImgUpload(file,uploadImgPath);
                    break;
            }
            //上传成功
            return Result.ok("文件上传成功！");
        } catch (Exception e) {
            //上传失败
            return Result.err(Code.CODE_ERR_BUSINESS,"文件上传失败");
        }
    }

    @ApiOperation("图片或视频删除")
    @GetMapping("/img-video-delete/{name}")
    public Result deleteImg(@PathVariable String name) throws IOException {

        Result result = videoUtil.videoOrImgDelete(name);

        return result;
    }

    @ApiOperation("文章删除")
    @GetMapping("/delete-forum/{forumId}")
    public Result deleteForum(@PathVariable Integer forumId){

        Result result = forumService.deleteForum(forumId);

        return result;
    }

    @ApiOperation("文章修改")
    @PostMapping("/update-forum")
    public Result updateForum(@RequestBody VoForumId forum){

        Result result = forumService.updateForum(forum);

        return result;
    }

    @ApiOperation("根据ID查询文章")
    @GetMapping("/select-id/{id}")
    public Result selectById(@PathVariable Integer id){

        Result result = forumService.selectById(id);

        return result;
    }

    @ApiOperation("文章是否可见")
    @GetMapping("/isPrivacy-id/{id}")
    public Result updatePrivacy(@PathVariable Integer id){

        Result result = forumService.updatePrivacy(id);

        return result;
    }

    @ApiOperation("文章是否热门")
    @GetMapping("/isPopular-id/{forumId}")
    public Result updatePopular(@PathVariable Integer forumId){

        Result result = forumService.updatePopular(forumId);

        return result;
    }

    @ApiOperation("根据用户ID查询隐私文章")
    @GetMapping("/privacy-forum-userId/{userId}")
    public Result findPrivacyFormByUserId(@PathVariable Integer userId){

        Result result = forumService.findPrivacyFormByUserId(userId);

        return result;
    }

    @ApiOperation("根据用户ID查询文章")
    @GetMapping("/find-forum-userId/{userId}")
    public Result findFormByUserId(@PathVariable Integer userId){

        Result result = forumService.findFormByUserId(userId);

        return result;
    }


    @ApiOperation("根据文章ID删除文章")
    @DeleteMapping("/forum-delete-id/{forumId}")
    public Result forumDelById(@PathVariable Integer forumId){

        Result result = forumService.forumDelById(forumId);

        return result;
    }

}
