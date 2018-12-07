package com.garyhu.service;

import com.garyhu.domain.SysUser;
import com.garyhu.dto.UserDto;
import com.garyhu.pojo.Result;

import java.util.List;

/**
 * Created on 2018/2/1 0001.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
public interface SysUserService {

    SysUser save(SysUser user);

    SysUser findByUsername(String username);

    List<UserDto> findAll();

    UserDto findOne(String id);

    Result save(String data);

    Result<String> delUsers(String ids);
}
