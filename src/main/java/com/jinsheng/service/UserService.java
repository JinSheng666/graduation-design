package com.jinsheng.service;

import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.User;
import com.jinsheng.dto.DtoUser;
import com.jinsheng.dto.DtoUserPassword;

public interface UserService {

    User findUserName(String userName);

    Result addUser(DtoUser user);

    Result findUserAll();

    Result updateUser(User user);

    Result updatePassword(DtoUserPassword password, String token);

    Result CurrentUser(String token);

    Result findPageUser(User user,Page page);

    Result loginUser(String token);

    Result getPersonalUser(Integer userId);

    Result jiaoyanPass(String password,String token);
}
