package com.yun.wx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

/**
 * 
 * @author wangkai
 * @2016年7月4日 下午4:51:58
 * @desc:微信公众号支付配置信息
 */
/*public class WeixinPayConfig {
	// 公众号appid
	public final static String APPID = "wx607616da7878fcbe"; //云姐
	// 公众号appsecret
	public final static String APPSECRET = "36f12994cb62015f99043d6a8a11e7cb";//云姐
	// 证书
//	public static String CERT_FILE = System.getProperty("user.dir")
//			+ System.getProperty("file.separator")+"fscert"+System.getProperty("file.separator")+"apiclient_cert.p12";//鱼说微信企业支付证书
	// 公众平台商户ID
	public final static String MCHID = "1504275441"; //
	// 公众平台商户KEY
	public final static String KEY = "1q2w3e4r5t6y7u8i9o0pasdfghjklzxc";
	// 微信商户平台支付结果通知URL
	// 线上环境 
    public final static String NOTIFY_URL = "https://pay.xxxxx.com/weixinpay/jsapi/callback/pay.action";
	// 统一下单URL
	public final static String WECHAT_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 查询订单URL
	public final static String WECHAT_ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	// 获取token接口(GET)
	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	// ticket 接口 (GET)
	public final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	// oauth2授权接口(GET)
	public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
}*/
@Component
public class WeixinPayConfig {
	public final static String APPID = "wx607616da7878fcbe"; //云姐
	// 公众号appsecret
	public final static String APPSECRET = "d2833e06fa3fbf24c184cfc004bb54de";//云姐
	// 证书
//	public static String CERT_FILE = System.getProperty("user.dir")
//			+ System.getProperty("file.separator")+"fscert"+System.getProperty("file.separator")+"apiclient_cert.p12";//鱼说微信企业支付证书
	// 公众平台商户ID
	public final static String MCHID = "1504275441"; //
	// 公众平台商户KEY
	public final static String KEY = "qazWSXedcRFVtgbYHNujmUJMikOLpQAZ";
	// 微信商户平台支付结果通知URL
	// 线上环境 
    public final static String NOTIFY_URL = "https://pay.xxxxx.com/weixinpay/jsapi/callback/pay.action";
	// 统一下单URL
	public final static String WECHAT_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 查询订单URL
	public final static String WECHAT_ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	// 获取token接口(GET)
	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	// ticket 接口 (GET)
	public final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	// oauth2授权接口(GET)
	public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId("wx607616da7878fcbe");
        wxPayH5Config.setMchId("1504275441");
        wxPayH5Config.setMchKey("d2833e06fa3fbf24c184cfc004bb54de");
        //wxPayH5Config.setKeyPath("/app.p12");
        wxPayH5Config.setNotifyUrl("http://www.lovehuayu.com/Yun/notify");
        return wxPayH5Config;
    }

    @Bean
    public BestPayServiceImpl bestPayService(WxPayH5Config wxPayH5Config) {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }
}