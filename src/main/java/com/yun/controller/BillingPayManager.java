package com.yun.controller;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yun.mybatis.mapper.BillingRecordMapper;
import com.yun.mybatis.mapper.OrderMapper;
import com.yun.mybatis.mapper.UserMapper;
import com.yun.mybatis.model.BillingRecord;
import com.yun.mybatis.model.Order;
import com.yun.mybatis.model.User;
import com.yun.util.HttpRequest;
import com.yun.wx.WXPay;
import com.yun.wx.WXPayConfigImpl;
import com.yun.wx.WXPayUtil;
import com.yun.wx.util.HttpClientUtil;
import com.yun.wx.util.PayCommonUtil;

@RestController
@RequestMapping(value = "/Yun")
public class BillingPayManager {
	private Logger logger = LoggerFactory
			.getLogger(BillingPayManager.class);
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BillingRecordMapper billingRecordMapper;
	@RequestMapping("WxPay")
    public String WxPay(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("WxPay Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		String orderId = request.getParameter("orderId");
        logger.info("****正在支付的orderId****" + orderId);
        Object o = session.getAttribute("userId");
        User user = userMapper.selectByPrimaryKey(o.toString());
      Map<String, String> r=new HashMap<String, String>();
        if(orderId == null){
        	r.put("rspCode", "000001");
        	return JSON.toJSONString(r);
        }
        int total_fee = 0; // 产品价格1分钱,用于测试
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
        	r.put("rspCode", "000002");
        	return JSON.toJSONString(r);
		}
        total_fee = order.getPrice();
        // 统一下单
        String out_trade_no = PayCommonUtil.CreateNoncestr();
        
        String spbill_create_ip = HttpClientUtil.getIpAddr(request);
        logger.info("支付IP=" + spbill_create_ip);
        if(spbill_create_ip == null || spbill_create_ip.equals("0:0:0:0:0:0:0:1")){
        	spbill_create_ip = "121.254.151.1";
        }
        logger.info("支付IP=" + spbill_create_ip);
        String nonce_str = PayCommonUtil.CreateNoncestr(); // 随机数据
        
        WXPayConfigImpl config = WXPayConfigImpl.getInstance();
        WXPay  wxpay = new WXPay(config,"http://www.lovehuayu.com/Yun/wxpay/notify", false, false);
        
        
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", out_trade_no);
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", total_fee+"");
        data.put("spbill_create_ip", spbill_create_ip);
        data.put("notify_url", "http://www.lovehuayu.com/Yun/wxpay/notify");
        data.put("trade_type", "NATIVE");
        data.put("product_id", "12");
        data.put("nonce_str", nonce_str);
        data.put("openid", user.getWxId());
        // data.put("time_expire", "20170112104120");
        logger.info(JSON.toJSONString(data));
        r = wxpay.unifiedOrder(data);
        try {
            
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        
        /**** 返回对象给页面 ****/
        r.put("rspCode", "000000");
        r.put("timeStamp",String.valueOf(WXPayUtil.getCurrentTimestamp()));
    	return JSON.toJSONString(r);
    }
	@RequestMapping(value = "/WxPayNew", method = RequestMethod.POST)
    public String orderPay(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("WxPayNew Handler,Parameter=："
				+ JSON.toJSONString(request.getParameterMap()));
		String orderId = request.getParameter("orderId");
        logger.info("****正在支付的orderId****" + orderId);
        Object o = session.getAttribute("userId");
        User user = userMapper.selectByPrimaryKey(o.toString());
      Map<String, String> r=new HashMap<String, String>();
        if(orderId == null){
        	r.put("rspCode", "000001");
        	return JSON.toJSONString(r);
        }
        int total_fee = 0; // 产品价格1分钱,用于测试
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
        	r.put("rspCode", "000002");
        	return JSON.toJSONString(r);
		}
        total_fee = order.getPrice();
		Map<String, String> putMap = new HashMap<String, String>();

		Map<String, String> paraMap = new HashMap<String, String>();
		String spbill_create_ip = HttpClientUtil.getIpAddr(request);
        logger.info("支付IP=" + spbill_create_ip);
        if(spbill_create_ip == null || spbill_create_ip.equals("0:0:0:0:0:0:0:1")){
        	spbill_create_ip = "121.254.151.1";
        }
	    String appid = "wx607616da7878fcbe";
	    String paternerKey = "qazWSXedcRFVtgbYHNujmUJMikOLpQAZ";
	   // String out_trade_no = PayCommonUtil.CreateNoncestr();
	    String body = getInterceptedStr(order.getGoodsName(), 12);
	    String nonce_str = WXPayUtil.generateNonceStr(); // 随机数据
	    String out_trade_no = orderId;
	    

        BillingRecord billingRecord=new BillingRecord();
        billingRecord.setErrorCode(0);
        billingRecord.setOrderNo(orderId);
        billingRecord.setOrderPar(nonce_str);
        billingRecord.setPayType(1);
        billingRecord.setProductIdx(0);
        billingRecord.setState(1);
        billingRecord.setTime(new Date());
        billingRecord.setTotalPrice(order.getPrice());
        billingRecord.setTransactionNo("");
        billingRecordMapper.insertSelective(billingRecord);
        
		paraMap.put("appid", appid);  
		paraMap.put("body", body);  
		paraMap.put("mch_id", "1504275441");  
		paraMap.put("nonce_str", nonce_str);  
		paraMap.put("openid", user.getWxId());
		paraMap.put("out_trade_no", out_trade_no);//订单号
		paraMap.put("spbill_create_ip", spbill_create_ip);  
		paraMap.put("total_fee","1");  
		paraMap.put("trade_type", "JSAPI");  
		paraMap.put("notify_url","http://www.lovehuayu.com/Yun/wxpay/notify");// 此路径是微信服务器调用支付结果通知路径随意写
		String sign = WXPayUtil.generateSignature(paraMap, paternerKey);
		paraMap.put("sign", sign);
		String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
					
		// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
		String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
		System.out.println(xmlStr);
		//以下内容是返回前端页面的json数据
		String prepay_id = "";//预支付id
		if (xmlStr.indexOf("SUCCESS") != -1) {  
			Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
			prepay_id = (String) map.get("prepay_id");  
		}
		Map<String, String> payMap = new HashMap<String, String>();
		payMap.put("appId", appid);  
		payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");  
		payMap.put("nonceStr", nonce_str);  
		payMap.put("signType", "MD5");  
		payMap.put("package", "prepay_id=" + prepay_id);  
		String paySign = WXPayUtil.generateSignature(payMap, paternerKey);  
		payMap.put("paySign", paySign);
		payMap.put("orderId", orderId);
		System.out.println(JSON.toJSONString(payMap));
		return JSON.toJSONString(payMap);

    }
	



	/** 对字符串进行MD5加密 */
	private String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/** 将一个字节转化成十六进制形式的字符串 */
	private String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	public static String  getInterceptedStr(String str,int len){//50
		String result = "";
		int strLength = str.length();
		if(len > strLength){
			result = str;
		}else{
			result = str.substring(0,len);
		}
		return result;
	}
	// 十六进制下数字到字符的映射数组
	private final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f" };
	public static void main(String[] args) throws Exception {
		String str = "ni shi wdds ";
		String a = getInterceptedStr(str, 8);
		System.out.println(a);
	}
}
