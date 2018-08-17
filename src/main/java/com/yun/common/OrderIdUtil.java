package com.yun.common;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class OrderIdUtil {
	private static final char[] numbers = new char[62]; //0-9 A-z
	 
    private static void init() {
        int size = numbers.length;
        for (int i = 0; i < size; i++) {
            if (i <= 9) numbers[i] = (char) (i + 48); //0-9
            else if (i <= 35) numbers[i] = (char) (i + 55);//A-Z
            else numbers[i] = (char) (i + 61);
        }
    }
 
    public static String getRandomString(int digite, boolean repeat) {
        if (digite > 35) return "位数太大就会变有序序列啦";
        init();
        StringBuffer stringBuffer = new StringBuffer();
        if (repeat) {
            int i = 0;
            while (i < digite) {
                stringBuffer.append(numbers[(int) (Math.random() * 62)]);
                i++;
            }
        } else {
            Set<Character> set = new HashSet<Character>();
            while (set.size() != digite) {
                char char2 = numbers[(int) (Math.random() * 62)];
                set.add(char2);
            }
            for (Character c : set) {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString().toUpperCase();
    }
    /** 
     * 20位末尾的数字id 
     */  
    public static int Guid=100;  

    public static String getGuid() {  
          
    	OrderIdUtil.Guid+=1;  

        long now = System.currentTimeMillis();    
        //获取4位年份数字    
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");    
        //获取时间戳    
        String time=dateFormat.format(now);  
        String info=now+"";  
        if(OrderIdUtil.Guid>999){  
        	OrderIdUtil.Guid=100;         
        }  
        return time+info.substring(2, info.length())+generateRandomNumber(3);    
    }
    public static long generateRandomNumber(int n){  
        if(n<1){  
            throw new IllegalArgumentException("随机数位数必须大于0");  
        }  
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);  
    }  
 
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println(OrderIdUtil.generateRandomNumber(10)+"");
    }
}
