package com.jinsheng.controller;

import com.jinsheng.utils.VideoOrImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "视频和图片接口")
@RestController
@CrossOrigin
@RequestMapping("/video-image")
public class VideoAndImageController {

    @Autowired
    private VideoOrImageUtil videoOrImageUtil;

    @ApiOperation("视频")
    @GetMapping("/video/{videoName}")
    public void getVideo(HttpServletRequest request, HttpServletResponse response, @PathVariable String videoName) throws IOException {

        videoOrImageUtil.getVideo(request,response,videoName);
    }

    @ApiOperation("图片")
    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {

        return videoOrImageUtil.getImage(imageName);
    }

}
