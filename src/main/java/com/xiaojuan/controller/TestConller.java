package com.xiaojuan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
public class TestConller {
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> postData = new HashMap<String, Object>();
		Map<String, Object> posts = new HashMap<String, Object>();
		
		postData.put("message", JSON.toJSONString(posts));
		return new ModelAndView("index", postData);
	}
}
