package com.yun.wx.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


/**
 * 微信签名帮助类 
 * 
 * @author tz
 *
 */
public class WxSignUtils {

	
    /**
     * 微信支付签名算法MD5
     * @param characterEncoding 编码格式  UTF-8 
     * @param parameters 参数
     * @param wxKey 微信key
     * @return  签名
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters,String wxKey){

        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) 
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key="+wxKey);
        //sb.append("key=" + Key);
        System.out.println("字符串拼接后是："+sb);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
//        String sign = encodeByMD5(sb.toString()).toUpperCase();
        return sign;
    }

    /** 
     * 是否签名正确,验证签名 
     * @return boolean 
     * @param  packageParams签名参数
     *  @param  characterEncoding 字符 UTF-8
     *   @param  packageParams签名参数
     */  
    public static boolean isWxPaySign(String characterEncoding, SortedMap<Object, Object> packageParams) {  
        StringBuffer sb = new StringBuffer();  
        Set es = packageParams.entrySet();  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            String v = (String)entry.getValue();  
            if(!"sign".equals(k) && null != v && !"".equals(v)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
          
        sb.append("key=" + LoadProperties.getPayProperty("wx_native_key"));  
          
        //算出摘要  
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();  
        String wxPaySign = ((String)packageParams.get("sign")).toLowerCase();  
          
        return wxPaySign.equals(mysign);  
    }  
    
}
