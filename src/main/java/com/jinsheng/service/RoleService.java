package com.jinsheng.service;

import com.jinsheng.common.Result;
import com.jinsheng.domain.Role;

public interface RoleService {

    Result findAllRole();

    Result updateRole(Role role);

    Result addRole(Role role);

}
