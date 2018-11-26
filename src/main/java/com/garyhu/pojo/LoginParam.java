package com.garyhu.pojo;

/**
 * @author: garyhu
 * @since: 2018/11/26 0026
 * @decription: 用户登录所需要的认证信息类
 */
public class LoginParam {

    private String username;
    private String password;
    private String clientId;
    private String captchCode;
    private String captchValue;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCaptchCode() {
        return captchCode;
    }

    public void setCaptchCode(String captchCode) {
        this.captchCode = captchCode;
    }

    public String getCaptchValue() {
        return captchValue;
    }

    public void setCaptchValue(String captchValue) {
        this.captchValue = captchValue;
    }
}
