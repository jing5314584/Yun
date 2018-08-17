package com.yun.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yun.mybatis.mapper.BillingRecordMapper;
import com.yun.mybatis.mapper.OrderMapper;
import com.yun.mybatis.mapper.UserMapper;
import com.yun.mybatis.model.BillingRecord;
import com.yun.mybatis.model.BillingRecordExample;
import com.yun.mybatis.model.Order;
import com.yun.wx.WXPayUtil;
import com.yun.wx.util.PayCommonUtil;

@RestController
@RequestMapping(value = "/Yun/wxpay")
public class BillingPayNotifyManager {
	private Logger logger = LoggerFactory
			.getLogger(BillingPayNotifyManager.class);
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BillingRecordMapper billingRecordMapper;
	/**				<xml><appid><![CDATA[wx607616da7878fcbe]]></appid>
2018-08-15T13:41:43.249682925Z <bank_type><![CDATA[CFT]]></bank_type>
2018-08-15T13:41:43.262920223Z <cash_fee><![CDATA[1]]></cash_fee>
2018-08-15T13:41:43.262954890Z <fee_type><![CDATA[CNY]]></fee_type>
2018-08-15T13:41:43.262959857Z <is_subscribe><![CDATA[Y]]></is_subscribe>
2018-08-15T13:41:43.262963078Z <mch_id><![CDATA[1504275441]]></mch_id>
2018-08-15T13:41:43.262968228Z <nonce_str><![CDATA[a4b55f4fc724470e9ce20a8e158f4f38]]></nonce_str>
2018-08-15T13:41:43.262971536Z <openid><![CDATA[oWmri0z_KZHvyV4UqOpoMoHe82yI]]></openid>
2018-08-15T13:41:43.262974544Z <out_trade_no><![CDATA[7sgsGCVQCXv3XUvl]]></out_trade_no>
2018-08-15T13:41:43.262977684Z <result_code><![CDATA[SUCCESS]]></result_code>
2018-08-15T13:41:43.262991454Z <return_code><![CDATA[SUCCESS]]></return_code>
2018-08-15T13:41:43.262994751Z <sign><![CDATA[60C41C32AEA4CC09AFF98A644EB16D7A]]></sign>
2018-08-15T13:41:43.262997566Z <time_end><![CDATA[20180815214142]]></time_end>
2018-08-15T13:41:43.263000329Z <total_fee>1</total_fee>
2018-08-15T13:41:43.263002986Z <trade_type><![CDATA[JSAPI]]></trade_type>
2018-08-15T13:41:43.263005704Z <transaction_id><![CDATA[4200000161201808158051012566]]></transaction_id>
2018-08-15T13:41:43.263008399Z </xml>
**/
	@RequestMapping("/notify")
	public String WxPay(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//logger.info("notify Handler,Parameter=："
		//		+ JSON.toJSONString(request.getParameterMap()));
		InputStream is = null;
		try {
			is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WXPayUtil.inputStream2String(is);
			logger.info("notify Handler,Parameter=："+xml);
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 将微信发的xml转map

			if (notifyMap.get("return_code").equals("SUCCESS")) {
				if (notifyMap.get("result_code").equals("SUCCESS")) {
					String ordersSn = notifyMap.get("out_trade_no");// 商户订单号
					String nonce_str = notifyMap.get("nonce_str");// 实际支付的订单金额:单位
					String transaction_id = notifyMap.get("transaction_id");//交易流水号
					logger.info("ordersSn:"+ordersSn+",nonce_str:"+nonce_str+",transaction_id:"+transaction_id);
					/*
					 * 以下是自己的业务处理------仅做参考 更新order对应字段/已支付金额/状态码
					 */
					BillingRecordExample example=new BillingRecordExample();
					example.createCriteria().andOrderParEqualTo(nonce_str).andStateEqualTo(1);
					List<BillingRecord> billingRecordlist = billingRecordMapper.selectByExample(example);
					if(billingRecordlist.size() == 1){
						BillingRecord billingRecord = billingRecordlist.get(0);
						//更新订单
						updateOrder(billingRecord,transaction_id,ordersSn);
						
					}else{
						logger.info("billingRecordlist size is null");
					}
					

				}
			}

			// 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
			response.getWriter()
					.write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void updateOrder(BillingRecord billingRecord, String transaction_id, String ordersSn) {
		// TODO Auto-generated method stub
		if(billingRecord != null){
			billingRecord.setState(2);
			billingRecord.setTime(new Date());
			billingRecord.setTransactionNo(transaction_id);
			billingRecordMapper.updateByPrimaryKeySelective(billingRecord);
			
			Order order = orderMapper.selectByPrimaryKey(ordersSn);
			if(order != null){
				order.setPayTime(new Date());
				order.setPayType(1);
				orderMapper.updateByPrimaryKeySelective(order);
			}else{
				logger.error("order is null!!!");
			}
			
		}
		
	}

	public static void main(String[] args) throws Exception {
		String out_trade_no = PayCommonUtil.createConceStr(20);
		int total_fee = 1; // 产品价格1分钱,用于测试
		String spbill_create_ip = "192.168.16.25";
		// logger.info("支付IP" + spbill_create_ip);
		String nonce_str = PayCommonUtil.createConceStr(20); // 随机数据
		System.out.println(out_trade_no);
		System.out.println(nonce_str);
		// WXPayConfigImpl config = WXPayConfigImpl.getInstance();
		// WXPay wxpay = new WXPay(config);

		/*
		 * HashMap<String, String> data = new HashMap<String, String>();
		 * data.put("body", "腾讯充值中心-QQ会员充值"); data.put("out_trade_no",
		 * out_trade_no); data.put("device_info", ""); data.put("fee_type",
		 * "CNY"); data.put("total_fee", "1"); data.put("spbill_create_ip",
		 * "123.12.12.123"); data.put("notify_url",
		 * "http://test.letiantian.me/wxpay/notify"); data.put("trade_type",
		 * "NATIVE"); data.put("product_id", "12"); // data.put("time_expire",
		 * "20170112104120");
		 * 
		 * try { Map<String, String> r = wxpay.unifiedOrder(data);
		 * System.out.println(r); } catch (Exception e) { e.printStackTrace(); }
		 */
	}
}
