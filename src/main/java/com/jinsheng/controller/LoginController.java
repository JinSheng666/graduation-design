package com.jinsheng.controller;


import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Result;
import com.jinsheng.domain.LoginUser;
import com.jinsheng.domain.User;
import com.jinsheng.service.UserService;
import com.jinsheng.utils.DigestUtil;
import com.jinsheng.utils.GraduationDesignConstants;
import com.jinsheng.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录接口")
//@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    //redis
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TokenUtils tokenUtils;


    /**
     * 登录
     * */
    @ApiOperation("登录")
    @PostMapping("/user/login")
    public Result loginUser(@RequestBody LoginUser loginUser){
        //验证码校验
        String code = loginUser.getVerificationCode();
        //判断验证码是否存在
        if (!redisTemplate.hasKey(code)){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"验证码错误");
        }
        //redisTemplate.delete(code);

        User user = userService.findUserName(loginUser.getUserName());

        if (user != null){
            //用户已审核
            if (user.getStatus().equals(GraduationDesignConstants.USER_STATE_PASS)){
                //拿到前端给的密码并进行加密
                String password = DigestUtil.hmacSign(loginUser.getPassword());

                if (password.equals(user.getPassword())){ //密码相等则合法-
                    //生成jwt token

                    //载体内容
                    CurrentUser cu = new CurrentUser();
                    cu.setUserId(user.getUserId());
                    cu.setCode(user.getCode());
                    cu.setUserName(user.getUserName());

                    //token生成
                    String token = tokenUtils.loginSign(cu, password);
                    //数据给到前端
                    return Result.result(Code.CODE_OK,true,"登录成功",token);

                }else{ //密码错误
                    return  Result.result(Code.CODE_ERR_BUSINESS,false,"密码错误！");
                }

            }else { //用户未审核
                return Result.result(Code.CODE_ERR_BUSINESS,false,"账户状态异常！");
            }
        }else {
            return Result.result(Code.CODE_ERR_BUSINESS,false,"账号不存在！");
        }
    }

    /**
     * 获得当前用户角色权限的信息
     * */
    /*@RequestMapping("/user/auth-list")
    public Result getAuthList(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        List<Auth> authList = authService.authTreeByUid(currentUser.getUserId());

        return Result.ok(authList);
    }*/
//    @CrossOrigin
    @ApiOperation("当前用户信息")
    @GetMapping("/user/info")
    public Result getUserMessage(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        Result result = userService.CurrentUser(token);

        return result;
    }

    /**
     * 退出系统logout
     * */
    @ApiOperation("退出系统")
    @PostMapping("/user/logout")
    public Result logout(@RequestHeader(GraduationDesignConstants.HEADER_TOKEN_NAME) String token){

        //redis中删除token
        redisTemplate.delete(token);

        //响应信息
        return Result.result(Code.CODE_OK,true,"系统已退出！");

    }

}
