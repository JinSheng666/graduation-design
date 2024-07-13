package com.jinsheng.controller;

import com.jinsheng.common.Result;
import com.jinsheng.domain.Role;
import com.jinsheng.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询所有角色")
    @GetMapping
    public Result findAllRole(){

        Result allRole = roleService.findAllRole();

        return allRole;
    }

    @ApiOperation("角色修改")
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    @ApiOperation("角色添加")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

}
