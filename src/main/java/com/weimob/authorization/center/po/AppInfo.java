package com.weimob.authorization.center.po;

/**
 *
 */
public class AppInfo {

    private String appId = "CFE4A97C4E2C202C869E813E67718241";

    private String appSecret = "FABDE81ECD6E4547B59E98AAFD50016A";

    /**
     * 引导用户授权获取code的接口
     */
    private String wechatAuthorizeUri = "";
    /**
     * 获取授权accessToken的接口
     */
    private String oauthAccessTokenUri = "https://dopen.weimob.com/fuwu/b/oauth2/token?";

    /**
     * 刷新公众账号accessToken的接口
     */
    private String appAccessTokenUri = "https://dopen.weimob.com/fuwu/b/oauth2/token?";

    /**
     * 获取模板列表uri
     */
    private String templateUri = "";

    /**
     * 发送模板消息
     */
    private String sendMessageUri = "";
    /**
     * 微信授权得到openId后，需要跳转主页
     */
    private String redirectHomeUri = "http://203.195.230.242:8080/weimob/authCenter/oauthCallBack.json";

    /**
     * 微信授权得到openId后，需要跳转的注册页
     */
    private String redirectRegisterUri = "";

    public String getTemplateUri() {
        return templateUri + "access_token=";
    }

    public void setTemplateUri(String templateUri) {
        this.templateUri = templateUri;
    }

    public String getSendMessageUri() {
        return sendMessageUri + "access_token=";
    }

    public void setSendMessageUri(String sendMessageUri) {
        this.sendMessageUri = sendMessageUri;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getWechatAuthorizeUri() {
        return wechatAuthorizeUri + "appid=" + appId;
    }

    public void setWechatAuthorizeUri(String wechatAuthorizeUri) {
        this.wechatAuthorizeUri = wechatAuthorizeUri;
    }

    public String getRedirectHomeUri() {
        return redirectHomeUri;
    }

    public void setRedirectHomeUri(String redirectUri) {
        this.redirectHomeUri = redirectUri;
    }


    public String getRedirectRegisterUri() {
        return redirectRegisterUri;
    }

    public void setRedirectRegisterUri(String redirectRegisterUri) {
        this.redirectRegisterUri = redirectRegisterUri;
    }

    public String getOauthAccessTokenUri() {
        return oauthAccessTokenUri + "client_id=" + appId + "&client_secret=" + appSecret;
    }

    public void setOauthAccessTokenUri(String oauthAccessTokenUri) {
        this.oauthAccessTokenUri = oauthAccessTokenUri;
    }

    public String getAppAccessTokenUri() {
        return appAccessTokenUri + "grant_type=refresh_token&" + "client_id=" + appId + "&client_secret=" + appSecret;
    }

    public void setAppAccessTokenUri(String appAccessTokenUri) {
        this.appAccessTokenUri = appAccessTokenUri;
    }

}
