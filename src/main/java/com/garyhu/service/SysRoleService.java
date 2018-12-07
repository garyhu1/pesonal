package com.garyhu.service;


import com.garyhu.domain.SysRole;
import com.garyhu.dto.RoleDto;
import com.garyhu.pojo.Result;

import java.util.List;

/**
 * Created on 2018/2/9.
 *
 * @author zlf
 * @since 1.0
 */
public interface SysRoleService extends MerryyouBaseService<SysRole>{

    List<RoleDto> findAlls();

    Result saveRole(String data);

    RoleDto findRole(String id);

    Result<String> delRoles(String ids);
}
