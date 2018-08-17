package com.yun.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.yun.common.OrderIdUtil;
import com.yun.common.UuidUtil;
import com.yun.mybatis.mapper.UserMapper;
import com.yun.mybatis.mapper.UserWxMapper;
import com.yun.mybatis.model.User;
import com.yun.mybatis.model.UserExample;
import com.yun.mybatis.model.UserWx;
import com.yun.wx.SNSUserInfo;
import com.yun.wx.WeixinOauth2Token;
import com.yun.wx.config.WeixinPayConfig;
import com.yun.wx.util.AdvancedUtil;

@Controller
@RequestMapping(value = "/Yun")
public class IndexContrller {
	private Logger logger = LoggerFactory
			.getLogger(IndexContrller.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserWxMapper userWxMapper;
	@RequestMapping(value = "/index")
	public String index(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("index Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		// 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if(code == null || state == null){
        	session.setAttribute("userId", "KRQTYQ8SAT5CPGNW5K8");
        	return "index";
        }
        String userId = "";
		// 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WeixinPayConfig.APPID, WeixinPayConfig.APPSECRET, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            if ("get_userinfo".equals(state)) {
            	SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
            	if (snsUserInfo!=null) {
//            		String nickName = new String(snsUserInfo.getNickname().getBytes("ISO-8859-1"), "UTF-8");
            		 System.out.println("得到的nickName="+snsUserInfo.getNickname());
            	
            		userId = UuidUtil.uuid();
            		User user=new User();
            		user.setAlias(snsUserInfo.getNickname());
            		user.setId(userId);
            		user.setLastLogin(new Date());
            		user.setPassword("");
            		user.setRegTime(new Date());
            		user.setUserName("");
            		user.setWxId(openId);
            		user.setShowId(OrderIdUtil.generateRandomNumber(10)+"");
            		userMapper.insertSelective(user);
            		
            		UserWx userWx=new UserWx();
            		userWx.setId(openId);
            		userWx.setUpdateDate(new Date());
            		userWx.setWxIcon(snsUserInfo.getHeadImgUrl());
            		userWx.setWxName(snsUserInfo.getNickname());
            		userWxMapper.insertSelective(userWx);
				} 
			}
            UserExample userExample=new UserExample();
            userExample.createCriteria().andWxIdEqualTo(openId);
            List<User> userList = userMapper.selectByExample(userExample);
            if(userList.size() <= 0){
            	 // 获取用户信息
            	return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeixinPayConfig.APPID+"&redirect_uri=http%3a%2f%2fwww.lovehuayu.com%2fYun%2findex.html&response_type=code&scope=snsapi_userinfo&state=get_userinfo#wechat_redirect";
            }else{
            	User user = userList.get(0);
            	userId = user.getId();
            	user.setLastLogin(new Date());
            	userMapper.updateByPrimaryKeySelective(user);
            }
            session.setAttribute("userId", userId);
            logger.info("userId="+userId);
        }
		return "index";
	}
	@RequestMapping(value = "/center")
	public String center(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "center";
	}
	@RequestMapping(value = "/p_detail")
	public String productDetail(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "productDetail";
	}
	@RequestMapping(value = "/cart")
	public String cart(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "cart";
	}
	@RequestMapping(value = "/buy")
	public String buy(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "buy";
	}
	@RequestMapping(value = "/addresslist")
	public String addresslist(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "addresslist";
	}
	@RequestMapping(value = "/addressedit")
	public String addressedit(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "addressedit";
	}
	@RequestMapping(value = "/about_us")
	public String aboutUs(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "about_us";
	}
	@RequestMapping(value = "/orderlist")
	public String orderlist(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "orderlist";
	}
	@RequestMapping(value = "/orderdetail")
	public String orderdetail(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		return "orderdetail";
	}
}
