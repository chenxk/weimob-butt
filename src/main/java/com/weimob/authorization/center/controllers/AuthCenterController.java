/*
 * ArrrowCtrl Inc., All rights reserved. 2017.
 * Oceanware component, Oceanware is a brand owned by ArrowCtrl.
 * Any use of this component should get permission from ArrowCtrl.
 */

package com.weimob.authorization.center.controllers;

import com.weimob.authorization.center.accesstoken.AccessTokenTools;
import com.weimob.authorization.center.accesstoken.HttpClientUtil;
import com.weimob.authorization.center.po.AppInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Description of AuthCenterController
 *
 * @author charles.chen
 * @version $Id:$
 * @created on 2017年3月12日
 */
@Api(tags = "authCenter", produces = "application/json")
@RestController
@RequestMapping(value = "/authCenter", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthCenterController {
    private static Logger logger = Logger.getLogger(AuthCenterController.class);

    private String charEncoding = "utf-8";

    private final String CODE = "code";
    private final String STATE = "state";

    @Autowired
    private AppInfo appInfo;

    @Autowired
    private AccessTokenTools tokenTools;


    public String getCharEncoding() {
        return charEncoding;
    }

    public void setCharEncoding(String charEncoding) {
        this.charEncoding = charEncoding;
    }


    /**
     *
     */
    @ApiOperation(value = "getWeimobCert", notes = "将微盟凭证appId封装返回，供前端跳转进行用户信息授权验证使用")
    @RequestMapping(value = "/getWeimobCert.json", method = RequestMethod.GET)
    public Object getWeimobCert(HttpServletRequest request, HttpServletResponse response) {
        //String userId = request.getParameter("userId");
        JSONObject json = new JSONObject();
        //json.put("openId", "");
        //json.put("userId", userId);
        json.put("appId", appInfo.getAppId());
        json.put("flag", true);
		/*if(!StringUtils.isEmpty(userId)){
			String openId = getOpenIdByUserId(userId);
			if(StringUtils.isEmpty(openId)){
				openId = "";
			}else{
				json.put("flag", true);
			}
			json.put("openId", openId);
		}*/
        logger.info("getWeimobCert:" + json);
        return json;
    }


    /**
     * @param request
     * @return
     */
    @ApiOperation(value = "oauthCallBack", notes = "用户授权后，微盟接口回调此接口")
    @RequestMapping(value = "/oauthCallBack.json", method = RequestMethod.GET)
    public void oauthCallBack(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter(CODE);
        String state = request.getParameter(STATE);
        logger.info("code:" + code);
        logger.info("state:" + state);

        if (StringUtils.isEmpty(code)) {
            logger.info("用户禁止授权");
            //put(state, "user auth failed");
        } else {

            String redirect = "";
            try {
                redirect = URLEncoder.encode(appInfo.getRedirectHomeUri(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String wechatAccessTokenUri = appInfo.getOauthAccessTokenUri();
            String fullUri = wechatAccessTokenUri + "&code=" + code + "&grant_type=authorization_code"
                    + "&redirect_uri=" + redirect + "&state=ABC";
            logger.info("authAccessTokenUrl:" + fullUri);

            String responseBody = HttpClientUtil.loadDataByPost(fullUri, null);
            logger.info("authAccessTokenUrl result:" + responseBody);
            if (!StringUtils.isEmpty(responseBody)) {
                JSONObject json = JSONObject.fromObject(responseBody);

                try {
                    String token = json.getString("access_token");
                    String refreshToken = json.getString("refresh_token");

                    tokenTools.setAccessToken(token);
                    tokenTools.setRefreshToken(refreshToken);
                    tokenTools.startThread(refreshToken);

                } catch (Exception e) {
                    // TODO: handle exception
                    logger.info("拉取openId失败", e);
                }
            } else {
                logger.info("获取accessToken失败");
                //put(state, "user auth failed");
            }
        }
    }

}
