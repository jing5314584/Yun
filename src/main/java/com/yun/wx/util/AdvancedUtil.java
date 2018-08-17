package com.yun.wx.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.yun.wx.SNSUserInfo;
import com.yun.wx.WeixinOauth2Token;

public class AdvancedUtil {
	/**
     * 获取网页授权凭证
     * 
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
	 * @throws Exception 
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) throws Exception {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        String jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
        JSONObject to = JSONObject.parseObject(jsonObject);
        System.out.println("获取Token："+JSON.toJSONString(to));
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(to.getString("access_token"));
                wat.setExpiresIn(to.getInteger("expires_in"));
                wat.setRefreshToken(to.getString("refresh_token"));
                wat.setOpenId(to.getString("openid"));
                wat.setScope(to.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = to.getInteger("errcode");
                String errorMsg = to.getString("errmsg");
                System.out.println("获取网页授权凭证失败 errcode:{"+errorCode+"} errmsg:{"+errorMsg+"}");
            }
        }
        return wat;
    }

	 public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) throws Exception {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        String jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
        JSONObject to = JSONObject.parseObject(jsonObject);
        System.out.println("获取用户信息："+JSON.toJSONString(to));
        String nick = EmojiParser.parseToHtmlDecimal(to.get("nickname")+"");
        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(to.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(nick);
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(to.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(to.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(to.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(to.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(to.getString("headimgurl"));
//                JSONArray privilege = to.getJSONArray("privilege");
                snsUserInfo.setPrivilegeList(JSONArray.parseArray(to.getJSONArray("privilege").toJSONString(), String.class));
                // 用户特权信息
//                snsUserInfo.setPrivilegeList(JSONArray.toList(to.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = to.getInteger("errcode");
                String errorMsg = to.getString("errmsg");
                System.out.println("获取网页授权凭证失败 errcode:{"+errorCode+"} errmsg:{"+errorMsg+"}");
            }
        }
        return snsUserInfo;
    }
	 /*public static SNSUserInfo getSNSUserInfo(String refresh_token, String openId,String appId) throws Exception {
	        SNSUserInfo snsUserInfo = null;
	        // 拼接请求地址
	        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	        requestUrl = requestUrl.replace("REFRESH_TOKEN", refresh_token).replace("APPID", appId);
	        // 通过网页授权获取用户信息
	        String jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);
	        JSONObject to = JSONObject.parseObject(jsonObject);
	        System.out.println("获取用户信息："+JSON.toJSONString(to));
	        if (null != jsonObject) {
	            try {
	                snsUserInfo = new SNSUserInfo();
	                // 用户的标识
	                snsUserInfo.setOpenId(to.getString("openid"));
	                // 昵称
	                snsUserInfo.setNickname(to.getString("nickname"));
	                // 性别（1是男性，2是女性，0是未知）
	                snsUserInfo.setSex(to.getInteger("sex"));
	                // 用户所在国家
	                snsUserInfo.setCountry(to.getString("country"));
	                // 用户所在省份
	                snsUserInfo.setProvince(to.getString("province"));
	                // 用户所在城市
	                snsUserInfo.setCity(to.getString("city"));
	                // 用户头像
	                snsUserInfo.setHeadImgUrl(to.getString("headimgurl"));
//	                JSONArray privilege = to.getJSONArray("privilege");
	                snsUserInfo.setPrivilegeList(JSONArray.parseArray(to.getJSONArray("privilege").toJSONString(), String.class));
	                // 用户特权信息
//	                snsUserInfo.setPrivilegeList(JSONArray.toList(to.getJSONArray("privilege"), List.class));
	            } catch (Exception e) {
	                snsUserInfo = null;
	                int errorCode = to.getInteger("errcode");
	                String errorMsg = to.getString("errmsg");
	                System.out.println("获取网页授权凭证失败 errcode:{"+errorCode+"} errmsg:{"+errorMsg+"}");
	            }
	        }
	        return snsUserInfo;
	    }*/
	 public static void main(String[] args) throws Exception {
//		 getSNSUserInfo("10_uf53c66IE86v2KwmdOGJW5d2zr7K-PcJWPwfSmJP7tez-V6TmQqvxT7-mqh8f9XG0SjXCc1QsFqAzWGTX89mdxiOhlEBsmHw16vtPEDSfHA", "", "wx607616da7878fcbe");
	}
}

