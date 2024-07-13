package com.jinsheng.controller;

import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Copywriting;
import com.jinsheng.service.CopywritingService;
import com.jinsheng.utils.GraduationDesignConstants;
import com.jinsheng.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "文案接口")
@RestController
@RequestMapping("/copy-writings")
public class CopywritingController {

    @Autowired
    private CopywritingService cwService;

    @Autowired
    private TokenUtils tokenUtils;

    @ApiOperation("文案选择")
    @GetMapping
    public Result copyWriting(){

        Result result = cwService.copyWriting();

        return result;
    }

    @ApiOperation("文案分页查询")
    @PostMapping("/copy-writing-page")
    public Result copyWritingPage(Copywriting copywriting, Page page){

        page.setPageNum(1);
        page.setPageSize(5);

        Result result = cwService.copyWritingPage(copywriting, page);

        return result;
    }

    @ApiOperation("文案修改")
    @PostMapping("/update-copy-writing")
    public Result updateCopeWriting(@RequestBody Copywriting copywriting){

        Result result = cwService.updateCopeWriting(copywriting);

        return result;
    }

    @ApiOperation("新增文案")
    @PostMapping("/add-copy-writing")
    public Result addCopeWriting(@RequestBody Copywriting copywriting,
                                 @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        copywriting.setUserId(currentUser.getUserId());

        Result result = cwService.addCopeWriting(copywriting);

        return result;
    }

    @ApiOperation("文案删除")
    @DeleteMapping("/delete-copy-writing")
    public Result deleteCopeWriting(@RequestBody List<Integer> cwIds){

        Result result = cwService.deleteCopeWriting(cwIds);

        return result;
    }

    @ApiOperation("文案删除ById")
    @DeleteMapping("/delete-copywriting-byId/{id}")
    public Result deleteCopeWritingById(@PathVariable Integer id){

        Result result = cwService.deleteCopeWritingById(id);

        return result;
    }

    @ApiOperation("根据用户ID查询文案")
    @GetMapping("/userId-copy-writing")
    public Result findCopyWritingByUserId(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = cwService.findCopyWritingByUserId(token);

        return result;
    }

    @ApiOperation("根据用户ID查询文案")
    @GetMapping("/userId-copy-writing/{userId}")
    public Result getCopyWritingByUserId(@PathVariable Integer userId){

        Result result = cwService.getCopyWritingByUserId(userId);

        return result;
    }

    @ApiOperation("根据文案ID查询文案")
    @GetMapping("/copy-writing-id/{id}")
    public Result copywritingById(@PathVariable Integer id){

        Result result = cwService.copywritingById(id);

        return result;
    }

}
