package com.garyhu.controller;

import com.garyhu.conf.Audience;
import com.garyhu.entity.User;
import com.garyhu.pojo.AccessToken;
import com.garyhu.pojo.LoginParam;
import com.garyhu.pojo.Result;
import com.garyhu.pojo.ResultCode;
import com.garyhu.repository.UserRepository;
import com.garyhu.utils.JWTHelper;
import com.garyhu.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: garyhu
 * @since: 2018/11/26 0026
 * @decription: 添加获取token 的接口类
 */
@RestController
public class AccountController {

    @Autowired
    private Audience audienceEntity;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/oauth/token")
    public Result getAccessToken(@RequestBody LoginParam loginParam){
        Result result;
        try{
            if(loginParam.getClientId() == null || (loginParam.getClientId().compareTo(audienceEntity.getClientId())) != 0){
                result = ResponseUtils.warn(ResultCode.INVALID_CLIENTID.getMsg(),ResultCode.INVALID_CLIENTID.getCode());

                return result;
            }
            // 缺少验证码校验

            // 验证用户名密码
            User user = userRepository.findUserByName(loginParam.getUsername());
            if(user == null){
                result = ResponseUtils.warn(ResultCode.INVALID_USER.getMsg(),ResultCode.INVALID_USER.getCode());
                return result;
            }else {
                String pwd = user.getPassword();

                if(!pwd.equals(loginParam.getPassword())){
                    result = ResponseUtils.warn(ResultCode.INVALID_PASSWORD.getMsg(),ResultCode.INVALID_PASSWORD.getCode());
                    return result;
                }
            }

            // 拼装accessToken
            String accessToken = JWTHelper.createJWT(loginParam.getUsername(),String.valueOf(user.getName()),
                    user.getRole(),audienceEntity.getClientId(),audienceEntity.getName(),
                    audienceEntity.getExpiresSeconds()*1000,audienceEntity.getBase64Secret());

            // 返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccessToken(accessToken);
            accessTokenEntity.setExpiresIn(audienceEntity.getExpiresSeconds());
            accessTokenEntity.setTokenType("bearer");

            result = ResponseUtils.success(accessTokenEntity);
            return result;
        }catch (Exception e){
            result = ResponseUtils.warn(ResultCode.SYSTEM_ERR.getMsg(),ResultCode.SYSTEM_ERR.getCode());
            return result;
        }
    }
}
