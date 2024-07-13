package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Role;
import com.jinsheng.mapper.RoleMapper;
import com.jinsheng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@CacheConfig(cacheNames = "com.jinsheng.service.impl.RoleServiceImpl")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色
     * */
    @Cacheable(key = "'all:role'")
    @Override
    public Result findAllRole() {

        List<Role> allRole = roleMapper.findAllRole();

        int code = !allRole.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

        Boolean flag = !allRole.isEmpty() ?true:false;

        String msg = !allRole.isEmpty() ?"角色查询成功！":"角色查询失败！";

        return Result.result(code,flag,msg,allRole);
    }

    /**
     * 修改角色
     * */
    @Override
    public Result updateRole(Role role) {

        int i = roleMapper.updateRole(role);

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

        Boolean flag = i>0 ?true:false;

        String msg = i>0 ?"角色修改成功！":"角色修改失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 添加角色
     * */
    @Override
    public Result addRole(Role role) {

        int i = roleMapper.addRole(role);

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;

        Boolean flag = i>0 ?true:false;

        String msg = i>0 ?"角色添加成功！":"角色添加失败！";

        return Result.result(code,flag,msg);
    }
}
