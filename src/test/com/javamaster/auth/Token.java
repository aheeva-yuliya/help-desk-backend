package com.javamaster.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(final int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
