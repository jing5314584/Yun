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
import com.yun.mybatis.model.V_UserWx;
import com.yun.mybatis.model.V_UserWxExample;

@RestController
@RequestMapping(value = "/Yun")
public class YunMallController {
	private Logger logger = LoggerFactory
			.getLogger(YunMallController.class);
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
	@RequestMapping(value = "/Slides")
	public String Slides(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getSlides Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		SlidesExample example=new SlidesExample();
		example.createCriteria().andTypeEqualTo(1);
		example.setOrderByClause("sort desc");
		List<Slides> slides = slidesMapper.selectByExample(example);
		return JSON.toJSONString(slides);
	}
	@RequestMapping(value = "/GetProducts")
	public String GetProducts(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("GetProducts Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		GoodsExample example=new GoodsExample();
		example.createCriteria().andIsRealEqualTo(true).andIsDeleteEqualTo(false);
		List<Goods> goods = goodsMapper.selectByExample(example);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("goods", goods);
		return JSON.toJSONString(param);
	}
	
	@RequestMapping(value = "/product/info")
	public String Detail(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("product/info Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Goods goods = null;
		if (id != 0) {
			goods = goodsMapper.selectByPrimaryKey(id);
		}
		Object o = session.getAttribute("userId");
		int addressIdx  = 0;
		
		List<V_GroupOrder> groupOrderList=new ArrayList<V_GroupOrder>();
		if(id != 0){
			V_GroupOrderExample example=new V_GroupOrderExample();
			example.createCriteria().andGoodsIdEqualTo(id).andTypeEqualTo(GroupOrderType.FAQIGroupBuy.getState());
			groupOrderList = groupOrderMapper.selectByExample(example);
		}
		if (o!=null) {
			if (!"".equals(o.toString())) {
				UserAddressExample example=new UserAddressExample();
				example.createCriteria().andUserIdEqualTo(o.toString());
				List<UserAddress> addressList = userAddressMapper.selectByExample(example);
				if (addressList.size() > 0) {
					addressIdx = addressList.get(0).getId();
				}
			}
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("goods", goods);
		param.put("addressIdx", addressIdx);
		param.put("groupOrderList", groupOrderList);
		return JSON.toJSONString(param);
	}
	@RequestMapping(value = "/addressDetail")
	public String addressDetail(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("addressDetail Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		UserAddress userAddress = null;
		if (id != 0) {
			userAddress = userAddressMapper.selectByPrimaryKey(id);
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userAddress", userAddress);
		return JSON.toJSONString(param);
	}
	@RequestMapping(value = "/getAddressList")
	public String getAddressList(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getAddressList Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		Object o = session.getAttribute("userId");
		List<UserAddress> addressList =new ArrayList<UserAddress>();
		if (o!=null) {
			if (!"".equals(o.toString())) {
				UserAddressExample example=new UserAddressExample();
				example.createCriteria().andUserIdEqualTo(o.toString());
				addressList = userAddressMapper.selectByExample(example);
			}
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("addressList", addressList);
		return JSON.toJSONString(param);
	}
	@RequestMapping(value = "/addressEdit")
	public ResponseData addressEdit(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("addressEdit Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		String address = request.getParameter("address");
		
		String mobile = request.getParameter("mobile");
		String name = request.getParameter("name");
		String district = request.getParameter("district");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		Object o = session.getAttribute("userId");
		UserAddress userAddress=null;
		if (o!=null) {
			if (!"".equals(o.toString())) {
				if(id != 0){
					userAddress = userAddressMapper.selectByPrimaryKey(id);
					if(userAddress != null){
						userAddress.setAddress(address);
						userAddress.setCity(city);
						userAddress.setConsignee(name);
						userAddress.setDistrict(district);
						userAddress.setMobile(mobile);
						userAddress.setProvince(province);
						userAddress.setUserId(o.toString());
						userAddressMapper.updateByPrimaryKeySelective(userAddress);
					}
				}else{
					return new ResponseData(ExceptionMsg.ParamError);
				}
			}else{
				return new ResponseData(ExceptionMsg.ParamError);
			}
		}
		return new ResponseData(ExceptionMsg.SUCCESS);
	}
	@RequestMapping(value = "/addressDelete")
	public ResponseData addressDelete(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("addressDelete Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
		}
		if(id != 0){
			userAddressMapper.deleteByPrimaryKey(id);
		}
		return new ResponseData(ExceptionMsg.SUCCESS);
	}
	@RequestMapping(value = "/addressSave")
	public ResponseData addressSave(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("addressSave Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));

		String address = request.getParameter("address");
		
		String mobile = request.getParameter("mobile");
		String name = request.getParameter("name");
		String district = request.getParameter("district");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		Object o = session.getAttribute("userId");
		
		if (o!=null) {
			if (!"".equals(o.toString())) {
					UserAddress userAddress=new UserAddress();
						userAddress.setAddress(address);
						userAddress.setCity(city);
						userAddress.setConsignee(name);
						userAddress.setDistrict(district);
						userAddress.setMobile(mobile);
						userAddress.setProvince(province);
						userAddress.setUserId(o.toString());
						userAddressMapper.insertSelective(userAddress);
					
			}else{
				return new ResponseData(ExceptionMsg.ParamError);
			}
		}
		return new ResponseData(ExceptionMsg.SUCCESS);
	}
	@RequestMapping(value = "/createOrder")
	public ResponseData createOrder(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("createOrder Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		int addressIdx = 0;
		int productIdx = 0;
		int type = 1;// 拼团类型（1单独购买2发起拼团3参加拼团）
		Object o = session.getAttribute("userId");
		try {
			addressIdx = Integer.parseInt(request.getParameter("addressIdx"));
			productIdx = Integer.parseInt(request.getParameter("productIdx"));
			type = Integer.parseInt(request.getParameter("type"));
		} catch (Exception e) {
		}
		String parent = "";
		if (type == 3) {
			String orderId = request.getParameter("orderId");
			System.out.println(orderId);
			Order order = orderMapper.selectByPrimaryKey(orderId);
			
			if (order != null) {
				parent = orderId;
			} else {
				return new ResponseData(ExceptionMsg.ParamError);
			}
		}
		if (!"".equals(o.toString())) {
			String nickName = "";
			String icon = "";
			V_UserWxExample example=new V_UserWxExample();
			example.createCriteria().andIdEqualTo(o.toString());
			List<V_UserWx> userWx= userMapper.selectByExample(example);
			if (userWx.size() > 0) {
				nickName = userWx.get(0).getAlias();
				icon = userWx.get(0).getWxIcon();
			}
			
			String goodsImage = "";
			String goodsName = "";
			Goods goods = goodsMapper.selectByPrimaryKey(productIdx);
			if (goods != null) {
				goodsName = goods.getGoodsName();
				goodsImage = goods.getGoodsImg();
			}
			String id = OrderIdUtil.getGuid();
			System.out.println("ID=" + id);
			Order order = new Order();
			order.setCreateTime(new Date());
			order.setGoodsId(productIdx);
			order.setGoodsImg(goodsImage);
			order.setGoodsName(goodsName);
			order.setId(id);
			order.setIsComment(false);
			order.setPayTime(new Date());
			order.setPayType(0);
			order.setPrice(goods.getSinglePrice());
			order.setType(type);
			order.setUserId(o.toString());
			order.setParent(parent);
			orderMapper.insertSelective(order);

			System.out.println(order.getId());
			OrderAdd orderAdd = new OrderAdd();
			orderAdd.setId(OrderIdUtil.getRandomString(20, false));
			orderAdd.setAddressId(addressIdx);
			orderAdd.setNickName(nickName);
			orderAdd.setOrderId(order.getId());
			orderAdd.setUserIcon(icon);
			orderAdd.setUserId(o.toString());
			orderAddMapper.insertSelective(orderAdd);
			return new ResponseData(ExceptionMsg.SUCCESS, order);
		} else {
			return new ResponseData(ExceptionMsg.ParamError);
		}

	}
	
}
