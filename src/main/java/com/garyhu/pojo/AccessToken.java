package com.garyhu.pojo;

/**
 * @author: garyhu
 * @since: 2018/11/26 0026
 * @decription: JWT Token 的返回结果
 */
public class AccessToken {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String userID;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
