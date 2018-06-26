package com.weimob.authorization.center.accesstoken;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.weimob.authorization.center.po.AppInfo;

import net.sf.json.JSONObject;

/**
 * 刷新access_token
 *
 * @author charles.chen
 * @version $Id:$
 * @created on 2017年3月12日
 */
public class AccessTokenTools implements Runnable {

    private static Logger logger = Logger.getLogger(AccessTokenTools.class);

    @Autowired
    private AppInfo appInfo;

    /**
     * accessToken有效期为2小时，设置1小时刷新一下token
     */
    private long sleepTime = 60 * 60;

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 获取到的token
     */
    private String accessToken;

    private String refreshToken;


    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private boolean running;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public synchronized void startThread(String refreshToken) {
        this.refreshToken = refreshToken;
        if (!running) {
            new Thread(this).start();
            running = true;
        } else {
            loadAccessToken();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            loadAccessToken();
            try {
                Thread.sleep(sleepTime * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public synchronized String loadAccessToken() {
        String url = appInfo.getAppAccessTokenUri() + "&refresh_token=" + refreshToken;
        String responseBody = HttpClientUtil.loadDataByPost(url, null);
        logger.info("appAccessToken result:" + responseBody);
        if (!StringUtils.isEmpty(responseBody)) {
            JSONObject json = JSONObject.fromObject(responseBody);
            try {
                String token = json.getString("access_token");
                logger.info("new Token:" + token);
                refreshToken = json.getString("refresh_token");
                logger.info("new refreshToken:" + refreshToken);
                accessToken = token;
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("拉取accessToken失败", e);
                //accessToken = null;
            }
        }
        return accessToken;
    }

}
