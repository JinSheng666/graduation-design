package com.jinsheng.controller;


import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.User;
import com.jinsheng.dto.DtoUser;
import com.jinsheng.dto.DtoUserPassword;
import com.jinsheng.service.UserService;
import com.jinsheng.utils.GraduationDesignConstants;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.utils.VideoOrImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrImageUtil imgUpload;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * /img-upload
     * 图片上传
     * 参数MultipartFile file对象封装了上传的图片;
     * */
    @Value("${file.upload-img-path}") //配置文件中file.upload-path属性值注入
    private String uploadImgPath;

    /**
     * 用户注册
     */
    @ApiOperation("账户注册")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody DtoUser user){

        Result result = userService.addUser(user);

        return result;
    }

    /**
     * 查询所有用户
     * */
    @ApiOperation("查询所有用户")
    @GetMapping("/userAll")
    public Result findAllUser(){

        Result userAll = userService.findUserAll();

        return userAll;
    }

    /**
     * 用户修改
     * */
    @ApiOperation("用户信息修改")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){

        Result result = userService.updateUser(user);

        return result;
    }

    /**
     * 账户密码修改
     * */
    @ApiOperation("账户密码修改")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody DtoUserPassword password,
                                 @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = userService.updatePassword(password, token);

        return result;
    }

    /**
     * 账户密码校验
     * */
    @ApiOperation("账户密码校验")
    @GetMapping("/updatePassword/{password}")
    public Result updatePassword(@PathVariable String password,
                                 @RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = userService.jiaoyanPass(password,token);

        return result;
    }

    /**
     * 用户信息查询
     * */
    @ApiOperation("用户信息显示")
    @GetMapping("/currentUser")
    public Result CurrentUser(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = userService.CurrentUser(token);

        return result;
    }

    /**
     * 根据token获得用户id
     * */
    @ApiOperation("根据token获得用户id")
    @GetMapping("/getUserIdByToken")
    public Result getUserIdByToken(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        return Result.result(Code.CODE_OK,true,"查询成功！",currentUser.getUserId());
    }

    /**
     * 分页查询用户信息
     * */
    @ApiOperation("分页查询")
    @PostMapping("/page-user")
    public Result findPageUser(User user,Page page){

        page.setPageNum(1);
        page.setPageSize(5);

        Result pageUser = userService.findPageUser(user, page);

        return pageUser;
    }

    /**
     * 登录用户信息查询
     * */
    @ApiOperation("登录用户信息查询")
    @GetMapping("/loginUser")
    public Result loginUser(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = userService.loginUser(token);

        return result;
    }

    /**
     * 查看他人信息
     * */
    @ApiOperation("查看他人信息")
    @GetMapping("/getPersonalUser/{userId}")
    public Result getPersonalUser(@PathVariable Integer userId){

        Result result = userService.getPersonalUser(userId);

        return result;
    }

    /**
     * 图像上传
     * */
    @ApiOperation("头像上传")
    @PostMapping("/user-img-upload")
    public Result uploadImg(MultipartFile file){ //MultipartFile file

        //拿到上传的图片的名字
        String filename = file.getOriginalFilename();

        // 获取文件扩展名
        String fileExtension = filename.substring(filename.lastIndexOf('.') + 1);

        try {

            // 根据文件扩展名进行不同路径存储
            switch (fileExtension.toLowerCase()) {
                case "png":
                case "jpg":
                case "jpeg":
                case "gif":
                    //目标存储位置
                    imgUpload.videoOrImgUpload(file,uploadImgPath);
                    break;
            }
            //上传成功
            return Result.ok("文件上传成功！");
        } catch (Exception e) {
            //上传失败
            return Result.err(Code.CODE_ERR_BUSINESS,"文件上传失败");
        }
    }


}
