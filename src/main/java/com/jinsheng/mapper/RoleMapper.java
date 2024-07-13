package com.jinsheng.mapper;

import com.jinsheng.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> findAllRole();

    int updateRole(Role role);

    int addRole(Role role);

}
