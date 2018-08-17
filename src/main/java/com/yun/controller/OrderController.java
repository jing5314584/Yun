package com.yun.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.yun.common.GroupOrderType;
import com.yun.common.OrderIdUtil;
import com.yun.controller.result.ExceptionMsg;
import com.yun.controller.result.ResponseData;
import com.yun.mybatis.mapper.GoodsMapper;
import com.yun.mybatis.mapper.OrderAddMapper;
import com.yun.mybatis.mapper.OrderMapper;
import com.yun.mybatis.mapper.SlidesMapper;
import com.yun.mybatis.mapper.UserAddressMapper;
import com.yun.mybatis.mapper.UserWxMapper;
import com.yun.mybatis.mapper.V_GroupOrderMapper;
import com.yun.mybatis.mapper.V_UserWxMapper;
import com.yun.mybatis.model.Goods;
import com.yun.mybatis.model.GoodsExample;
import com.yun.mybatis.model.Order;
import com.yun.mybatis.model.OrderAdd;
import com.yun.mybatis.model.Slides;
import com.yun.mybatis.model.SlidesExample;
import com.yun.mybatis.model.UserAddress;
import com.yun.mybatis.model.UserAddressExample;
import com.yun.mybatis.model.UserWx;
import com.yun.mybatis.model.V_GroupOrder;
import com.yun.mybatis.model.V_GroupOrderExample;
import com.yun.mybatis.model.V_GroupOrderExample.Criteria;
import com.yun.mybatis.model.V_UserWx;
import com.yun.mybatis.model.V_UserWxExample;

@RestController
@RequestMapping(value = "/Yun")
public class OrderController {
	private Logger logger = LoggerFactory
			.getLogger(OrderController.class);
	@Autowired
	private SlidesMapper slidesMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private UserAddressMapper userAddressMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderAddMapper orderAddMapper;
	@Autowired
	private V_GroupOrderMapper groupOrderMapper;
	@Autowired
	private V_UserWxMapper userMapper;
	
	@RequestMapping(value = "/getorderlist")
	public ResponseData createOrder(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getorderlist Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		int type = 0;// 拼团类型（1单独购买2发起拼团3参加拼团）
		Object o = session.getAttribute("userId");
		try {
			type = Integer.parseInt(request.getParameter("type"));
		} catch (Exception e) {
		}
		String userIdx = "";
		if(o != null){
			userIdx = o.toString();
		}
		V_GroupOrderExample example = new V_GroupOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userIdx);
		
		
		List<V_GroupOrder> orderList = groupOrderMapper.selectByExample(example);
		System.out.println(orderList.size());
		return new ResponseData(ExceptionMsg.SUCCESS, orderList);
		

	}
	@RequestMapping(value = "/getorderdetail")
	public ResponseData getorderdetail(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getorderdetail Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		Object o = session.getAttribute("userId");
		String orderid = request.getParameter("orderid");
		V_GroupOrderExample example = new V_GroupOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(orderid);
		
		V_GroupOrder v=null;
		List<V_GroupOrder> orderList = groupOrderMapper.selectByExample(example);
		if(orderList.size() > 0){
			v = orderList.get(0);
		}
		System.out.println(orderList.size());
		return new ResponseData(ExceptionMsg.SUCCESS, v);
		

	}
	
}
