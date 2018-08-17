package com.yun.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lly835.bestpay.config.SignType;
import com.yun.wx.WXPayConstants;
import com.yun.wx.config.WxPayConstants;

public class WXPayUtil {
	  private static Logger logger = LoggerFactory.getLogger(WXPayUtil.class); 
	    /**
	     * 根据code获取openid
	     * @param code
	     * @return
	     * @throws IOException
	     */
	     public static Map<String,Object> getOpenIdByCode(String code) throws IOException {
	        //请求该链接获取access_token
	        HttpPost httppost = new HttpPost("https://api.weixin.qq.com/sns/oauth2/access_token");
	        //组装请求参数
	        String reqEntityStr = "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	        reqEntityStr = reqEntityStr.replace("APPID", WxPayConstants.APPID);
	        reqEntityStr = reqEntityStr.replace("SECRET", WxPayConstants.APP_SECRET);
	        reqEntityStr = reqEntityStr.replace("CODE", code);
	        StringEntity reqEntity = new StringEntity(reqEntityStr);
	        //设置参数
	        httppost.setEntity(reqEntity);
	        //设置浏览器
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        //发起请求
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        //获得请求内容
	        String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
	        //获取openid
	        JSONObject jsonObject = JSONObject.parseObject(strResult);
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("openid",jsonObject.get("openid"));
	        map.put("access_token",jsonObject.get("access_token"));
	        map.put("refresh_token",jsonObject.get("refresh_token"));
	        return map;
	    }
	    /**
	     * 统一下单
	     * @param body
	     * @param out_trade_no
	     * @param total_fee
	     * @param IP
	     * @param notify_url
	     * @param openid
	     * @return
	     * @throws Exception 
	     */
	    public static String unifiedOrder(String body,String out_trade_no,Integer total_fee,String IP,String openid)throws Exception {
	        //设置访问路径
	        HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");

	        String nonce_str = getNonceStr().toUpperCase();//随机
	        //组装请求参数,按照ASCII排序
	        String sign = "appid=" + WxPayConstants.APPID +
	                        "&body=" + body +
	                        "&mch_id=" + WxPayConstants.MCH_ID +
	                        "&nonce_str=" + nonce_str +
	                        "&notify_url=" + WxPayConstants.NOTIFY_URL +
	                        "&openid=" + openid +
	                        "&out_trade_no=" + out_trade_no +
	                        "&spbill_create_ip=" + IP +
	                        "&total_fee=" + total_fee.toString() +
	                        "&trade_type=" + WxPayConstants.TRADE_TYPE_JS ;
	                        /*+ 
	                        "&key=" + WxPayConstants.KEY;*///这个字段是用于之后MD5加密的，字段要按照ascii码顺序排序
	        
	        sign =MD5(sign);

	        //组装包含openid用于请求统一下单返回结果的XML
	        StringBuilder sb = new StringBuilder("");
	        sb.append("<xml>");
	        setXmlKV(sb,"appid",WxPayConstants.APPID);
	        setXmlKV(sb,"body",body);
	        setXmlKV(sb,"mch_id",WxPayConstants.MCH_ID);
	        setXmlKV(sb,"nonce_str",nonce_str);
	        setXmlKV(sb,"notify_url",WxPayConstants.NOTIFY_URL);
	        setXmlKV(sb,"openid",openid);
	        setXmlKV(sb,"out_trade_no",out_trade_no);
	        setXmlKV(sb,"spbill_create_ip",IP);
	        setXmlKV(sb,"total_fee",total_fee.toString());
	        setXmlKV(sb,"trade_type",WxPayConstants.TRADE_TYPE_JS);
	        setXmlKV(sb,"sign",sign);
	        sb.append("</xml>");
	        System.out.println("统一下单请求：" + sb);

	        StringEntity reqEntity = new StringEntity(new String (sb.toString().getBytes("UTF-8"),"ISO8859-1"));//这个处理是为了防止传中文的时候出现签名错误
	        httppost.setEntity(reqEntity);
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
	        System.out.println("统一下单返回xml：" + strResult);

	        return strResult;
	    }
	    public static String MD5(String data) throws Exception {
	        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(data.getBytes("UTF-8"));
	        StringBuilder sb = new StringBuilder();
	        for (byte item : array) {
	            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
	        }
	        return sb.toString().toUpperCase();
	    }
	    /**
	     * 根据统一下单返回预支付订单的id和其他信息生成签名并拼装为map（调用微信支付）
	     * @param prePayInfoXml
	     * @return
	     * @throws Exception 
	     */
	    public static Map<String,Object> getPayMap(String prePayInfoXml) throws Exception{
	        Map<String,Object> map = new HashMap<String,Object>();

	        String prepay_id = getXmlPara(prePayInfoXml,"prepay_id");//统一下单返回xml中prepay_id
	        String timeStamp = String.valueOf((System.currentTimeMillis()/1000));//1970年到现在的秒数
	        String nonceStr = getNonceStr().toUpperCase();//随机数据字符串
	        String packageStr = "prepay_id=" + prepay_id;
	        String signType = "MD5";
	        String paySign =
	                "appId=" + WxPayConstants.APPID +
	                "&nonceStr=" + nonceStr +
	                "&package=prepay_id=" + prepay_id +
	                "&signType=" + signType +
	                "&timeStamp=" + timeStamp +
	                "&key="+ WxPayConstants.KEY;//注意这里的参数要根据ASCII码 排序
	        paySign =MD5(paySign);
	        map.put("appId",WxPayConstants.APPID);
	        map.put("timeStamp",timeStamp);
	        map.put("nonceStr",nonceStr);
	        map.put("packageStr",packageStr);
	        map.put("signType",signType);
	        map.put("paySign",paySign);
	        map.put("prepay_id",prepay_id);
	        return map;
	    }
	    
	    /**
	     * 修改订单状态，获取微信回调结果
	     * @param request
	     * @return
	     * @throws Exception 
	     */
	    public static String getNotifyResult(HttpServletRequest request) throws Exception{
	        String inputLine;  
	        String notifyXml = "";
	        String resXml = "";
	        try {  
	            while ((inputLine = request.getReader().readLine()) != null){  
	                notifyXml += inputLine;  
	            }  
	            request.getReader().close();  
	        } catch (Exception e) {  
	            logger.debug("xml获取失败：" + e);
	            e.printStackTrace();
	        }
	        System.out.println("接收到的xml：" + notifyXml);
	        logger.debug("收到微信异步回调：");  
	        logger.debug(notifyXml);  
	        if(notifyXml == null || notifyXml.equals("")){  
	            logger.debug("xml为空：");  
	        }

	        String appid = getXmlPara(notifyXml,"appid");;  
	        String bank_type = getXmlPara(notifyXml,"bank_type");  
	        String cash_fee = getXmlPara(notifyXml,"cash_fee");
	        String fee_type = getXmlPara(notifyXml,"fee_type");  
	        String is_subscribe = getXmlPara(notifyXml,"is_subscribe");  
	        String mch_id = getXmlPara(notifyXml,"mch_id");  
	        String nonce_str = getXmlPara(notifyXml,"nonce_str");  
	        String openid = getXmlPara(notifyXml,"openid");  
	        String out_trade_no = getXmlPara(notifyXml,"out_trade_no");
	        String result_code = getXmlPara(notifyXml,"result_code");
	        String return_code = getXmlPara(notifyXml,"return_code");
	        String sign = getXmlPara(notifyXml,"sign");
	        String time_end = getXmlPara(notifyXml,"time_end");
	        String total_fee = getXmlPara(notifyXml,"total_fee");
	        String trade_type = getXmlPara(notifyXml,"trade_type");
	        String transaction_id = getXmlPara(notifyXml,"transaction_id");

	        //根据返回xml计算本地签名
	        String localSign =
	                "appid=" + appid +
	                "&bank_type=" + bank_type +
	                "&cash_fee=" + cash_fee +
	                "&fee_type=" + fee_type +
	                "&is_subscribe=" + is_subscribe +
	                "&mch_id=" + mch_id +
	                "&nonce_str=" + nonce_str +
	                "&openid=" + openid +
	                "&out_trade_no=" + out_trade_no +
	                "&result_code=" + result_code +
	                "&return_code=" + return_code +
	                "&time_end=" + time_end +
	                "&total_fee=" + total_fee +
	                "&trade_type=" + trade_type +
	                "&transaction_id=" + transaction_id +
	                "&key=" + WxPayConstants.KEY;//注意这里的参数要根据ASCII码 排序
	        localSign = MD5(localSign);
	        System.out.println("本地签名是：" + localSign);
	        logger.debug("本地签名是：" + localSign);  
	        logger.debug("微信支付签名是：" + sign);

	        //本地计算签名与微信返回签名不同||返回结果为不成功
	        if(!sign.equals(localSign) || !"SUCCESS".equals(result_code) || !"SUCCESS".equals(return_code)){
	            System.out.println("验证签名失败或返回错误结果码"); 
	            logger.error("验证签名失败或返回错误结果码");
	            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
	        }else{
	             System.out.println("支付成功");
	             logger.debug("公众号支付成功，out_trade_no(订单号)为：" + out_trade_no);
	             resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
	        }
	        return resXml;
	    }
	    /**
	     * 获取32位随机字符串
	     * @return
	     */
	    public static String getNonceStr(){
	        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        StringBuilder sb = new StringBuilder();
	        Random rd = new Random();
	        for(int i = 0 ; i < 32 ; i ++ ){
	            sb.append(str.charAt(rd.nextInt(str.length())));
	        }
	        return sb.toString();
	    }
	    /**
	     * 插入XML标签
	     * @param sb
	     * @param Key
	     * @param value
	     * @return
	     */
	    public static StringBuilder setXmlKV(StringBuilder sb,String Key,String value){
	        sb.append("<");
	        sb.append(Key);
	        sb.append(">");

	        sb.append(value);

	        sb.append("</");
	        sb.append(Key);
	        sb.append(">");

	        return sb;
	    }

	    /**
	     * 解析XML 获得名称为para的参数值
	     * @param xml
	     * @param para
	     * @return
	     */
	    public static String getXmlPara(String xml,String para){
	        int start = xml.indexOf("<"+para+">");
	        int end = xml.indexOf("</"+para+">");

	        if(start < 0 && end < 0){
	            return null;
	        }
	        return xml.substring(start + ("<"+para+">").length(),end).replace("<![CDATA[","").replace("]]>","");
	    }

	    /**
	     * 获取ip地址
	     * @param request
	     * @return
	     */
	    public static String getIpAddr(HttpServletRequest request) {  
	        InetAddress addr = null;  
	        try {  
	            addr = InetAddress.getLocalHost();  
	        } catch (UnknownHostException e) {  
	            return request.getRemoteAddr();  
	        }  
	        byte[] ipAddr = addr.getAddress();  
	        String ipAddrStr = "";  
	        for (int i = 0; i < ipAddr.length; i++) {  
	            if (i > 0) {  
	                ipAddrStr += ".";  
	            }  
	            ipAddrStr += ipAddr[i] & 0xFF;  
	        }  
	        return ipAddrStr;  
	    }
	}

