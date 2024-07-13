package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.service.PopularService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "热门榜单接口")
@RestController
@RequestMapping("/populars")
public class PopularController {

    @Autowired
    private PopularService popularService;

    @ApiOperation("热门榜标题")
    @GetMapping
    public Result findPopular(){

        Result popular = popularService.findPopular();

        return popular;
    }

    @ApiOperation("热门榜单内容")
    @GetMapping("/popular-id/{popularId}")
    public Result popularById(@PathVariable Integer popularId){

        Result result = popularService.popularById(popularId);

        return result;
    }

    @ApiOperation("删除榜单")
    @GetMapping("/delete-popular/{popularId}")
    public Result deletePopularById(@PathVariable Integer popularId){

        Result result = popularService.deletePopularById(popularId);

        return result;
    }


}
