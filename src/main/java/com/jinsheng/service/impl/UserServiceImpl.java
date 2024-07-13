package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.User;
import com.jinsheng.dto.DtoUser;
import com.jinsheng.dto.DtoUserPassword;
import com.jinsheng.mapper.UserMapper;
import com.jinsheng.service.UserService;
import com.jinsheng.utils.CodeGenerate;
import com.jinsheng.utils.DigestUtil;
import com.jinsheng.utils.TokenUtils;
import com.jinsheng.vo.VoCurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
@CacheConfig(cacheNames = "com.jinsheng.service.impl.UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Value("${http.access-img-path}")
    private String httpGetImgPath;

    /**
     * 根据账户名查询数据
     * */
    @Override
    public User findUserName(String userName) {
        User user = userMapper.findUserName(userName);
        return user;
    }

    /**
     * 用户注册
     * */
    @Override
    public Result addUser(DtoUser user) {

        /*昵称不可重复，如若重复返回错误*/
        User userName = userMapper.findUserName(user.getUserName());
        if (userName != null){
            return Result.result(Code.CODE_ERR_BUSINESS,false,"用户名已存在！");
        }
        /*密码加密*/
        String password = DigestUtil.hmacSign(user.getPassword());
        user.setPassword(password);
        /*生成编码*/
        String generate = CodeGenerate.generate();
        User code = userMapper.findCode(generate);
        /*判断生成的编码是否存在，存在就重新生成*/
        if (code != null){
            generate = CodeGenerate.generate();
        }
        user.setCode(generate);
        //默认头像
        user.setHeadSculpture(httpGetImgPath+"default.jpg");

        int i = userMapper.addUser(user);

        if (i>0){
            return Result.result(Code.CODE_OK,true,"账户注册成功！");
        }
        return Result.result(Code.CODE_ERR_BUSINESS,false,"账户注册失败!！");
    }

    /**
     * 查询所有用户
     * */
    @Override
    public Result findUserAll() {

        List<User> allUser = userMapper.findAllUser();

        allUser.remove("password");

        int code = !allUser.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

        Boolean flag = !allUser.isEmpty() ? true:false;

        String msg = !allUser.isEmpty() ?"查询所有用户成功！":"查询所有用户失败";

        return Result.result(code,flag,msg,allUser);
    }

    /**
     * 用户信息修改
     * */
    @Override
    public Result updateUser(User user) {

        int i = userMapper.updateUser(user);

        String msg = i > 0 ? "用户信息修改成功！":"用户信息修改失败！";

        Integer code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

        Boolean flag = i > 0 ? true : false;

        return Result.result(code,flag,msg);
    }

    /**
     * 账户密码修改
     * */
    @Override
    public Result updatePassword(DtoUserPassword password, String token) {

        //通过token获得当前用户信息
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //根据拿到的用户信息查询
        User user = userMapper.findUserName(currentUser.getUserName());
        //前端传过来的密码加密

        log.debug("onepassword:"+password.getOnePassword());

        String onePassword = DigestUtil.hmacSign(password.getOnePassword());

        //判断密码是否正确，正确则可修改密码
        if (user.getPassword().equals(onePassword)){
            /*密码加密*/
            String twoPassword = DigestUtil.hmacSign(password.getTwoPassword());

            user.setPassword(twoPassword);

            int i = userMapper.updatePassword(user);

            String msg = i > 0 ? "密码修改成功！":"密码修改失败！";

            Integer code = i > 0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

            Boolean flag = i > 0 ? true : false;

            return Result.result(code,flag,msg);
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"密码错误！");
    }

    /**
     * 当前用户信息
     * */
//    @Cacheable(key = "'user:all'")
    @Override
    public Result CurrentUser(String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        VoCurrentUser voCurrentUser = userMapper.CurrentUser(currentUser.getUserName());

        String msg = voCurrentUser != null ? "当前用户查询成功！":"当前用户查询失败！";

        Integer code = voCurrentUser != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

        Boolean flag = voCurrentUser != null ? true : false;

        return Result.result(code,flag,msg,voCurrentUser);
    }

    /**
     * 分页查询
     * */
    @Override
    public Result findPageUser(User user, Page page) {

        if (user.getType() != null){
            Integer type = Integer.valueOf(user.getType());
            user.setIntType(type);
        }

        //查询行数
        int userRowCount = userMapper.findUserRowCount(user);
        //行数赋值
        page.setTotalNum(userRowCount);
        //获得查询的数据
        List<User> userPage = userMapper.findPageUser(user,page);

        //数据page统一接收
        page.setResultList(userPage);

        Integer code = userPage != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        String msg = userPage != null ? "分页查询成功！":"分页查询失败！";
        Boolean flag = userPage != null ? true:false;

        return Result.page(code,flag,msg,userPage,page.getTotalNum());
    }

    /**
     * 登录用户信息查询
     * */
    @Override
    public Result loginUser(String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        User loginUser = userMapper.getLoginUser(currentUser.getUserId());

        Integer code = loginUser != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        String msg = loginUser != null ? "用户信息查询成功！":"用户信息查询失败！";
        Boolean flag = loginUser != null ? true:false;

        return Result.result(code,flag,msg,loginUser);
    }

    /**
     * 查看他人信息
     * */
    @Override
    public Result getPersonalUser(Integer userId) {

        User personalUser = userMapper.getLoginUser(userId);

        Integer code = personalUser != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        String msg = personalUser != null ? "用户信息查询成功！":"用户信息查询失败！";
        Boolean flag = personalUser != null ? true:false;

        return Result.result(code,flag,msg,personalUser);
    }

    @Override
    public Result jiaoyanPass(String password,String token) {

        //通过token获得当前用户信息
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //根据拿到的用户信息查询
        User user = userMapper.findUserName(currentUser.getUserName());
        //前端传过来的密码加密

        log.debug("onepassword:"+password);

        String onePassword = DigestUtil.hmacSign(password);

        //判断密码是否正确，正确则可修改密码
        if (user.getPassword().equals(onePassword)){

            return Result.result(Code.CODE_OK,true,"密码校验成功！");
        }

        return Result.result(Code.CODE_ERR_BUSINESS,false,"密码错误！");

    }


}
