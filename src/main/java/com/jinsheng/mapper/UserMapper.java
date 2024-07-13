package com.jinsheng.mapper;

import com.jinsheng.common.Page;
import com.jinsheng.domain.User;
import com.jinsheng.dto.DtoUser;
import com.jinsheng.vo.VoCurrentUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User findUserName(String userName);

    int addUser(@Param("user") DtoUser user);

    User findCode(String code);

    List<User> findAllUser();

    int updateUser(@Param("user") User user);

    int updatePassword(User user);

    VoCurrentUser CurrentUser(String userName);

    int findUserRowCount(@Param("user") User user);

    List<User> findPageUser(@Param("user") User user, @Param("page") Page page);

    User getLoginUser(Integer userId);
}
