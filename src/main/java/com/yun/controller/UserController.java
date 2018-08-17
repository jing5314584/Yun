package com.yun.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yun.config.SystemConfig;
import com.yun.mybatis.mapper.V_UserWxMapper;
import com.yun.mybatis.model.V_UserWx;
import com.yun.mybatis.model.V_UserWxExample;

@RestController
@RequestMapping(value = "/Yun")
public class UserController {
	private Logger logger = LoggerFactory
			.getLogger(UserController.class);
	@Autowired
	private V_UserWxMapper userMapper;
	@RequestMapping(value = "/userInfo")
	public String userInfo(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("userInfo Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		
		V_UserWx user=null;
		Object o = session.getAttribute("userId");
		if (o != null ) {
			if(!"".equals(o.toString())){
				V_UserWxExample example=new V_UserWxExample();
				example.createCriteria().andIdEqualTo(o.toString());
				List<V_UserWx> userList = userMapper.selectByExample(example);
				if (userList.size()>0) {
					user = userList.get(0);
					logger.info("User:"+user.toString());
				}
			}
		}
		return JSON.toJSONString(user);
	}
	@RequestMapping(value = "/testV")
	public String testV(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("testV Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		String app =SystemConfig.getProperty("wx_app_id");
		return JSON.toJSONString(app);
	}
}
