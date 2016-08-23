package com.myzone.tool;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**   
 * @Title: StrUtils.java 
 * @Package com.myzone.tool 
 * @Description: 字符串操作
 * @author jiangyf   
 * @date 2016年1月8日 下午2:30:02 
 * @version V1.0   
 */
public class StrUtils {
	
	/** 
	* @Title: formateRate 
	* @Description: 保留小数点后两位 
	* @param rateDouble
	* @return String
	*/
	public static String formateRate(double rateDouble){  
    	String rateStr = String.valueOf(rateDouble);
        if(rateStr.indexOf(".") != -1){  
            //获取小数点的位置  
            int num = rateStr.indexOf(".");  
            //获取小数点后面的数字 是否有两位 不足两位补足两位  
            String dianAfter = rateStr.substring(0,num+1);  
            String afterData = rateStr.replace(dianAfter, "");  
            if(afterData.length() < 2){  
               afterData = afterData + "0" ;  
            } 
            return rateStr.substring(0,num) + "." + afterData.substring(0,2);  
         }else{  
           if(rateStr == "1"){  
              return "100";  
           }else{  
               return rateStr;  
           }  
         }  
	}
	
	private static Map<Integer, String> numberStringMap = new HashMap<Integer, String>();
    private static Map<Integer, String> base10StringMap = new HashMap<Integer, String>();
	static {  
        numberStringMap.put(0, "零");  
        numberStringMap.put(1, "一");  
        numberStringMap.put(2, "二");  
        numberStringMap.put(3, "三");  
        numberStringMap.put(4, "四");  
        numberStringMap.put(5, "五");  
        numberStringMap.put(6, "六");  
        numberStringMap.put(7, "七");  
        numberStringMap.put(8, "八");  
        numberStringMap.put(9, "九");  
  
        base10StringMap.put(1, "");  
        base10StringMap.put(2, "十");  
        base10StringMap.put(3, "百");  
        base10StringMap.put(4, "千");  
        base10StringMap.put(5, "万");  
    }  
    
    /** 
    * @Title: processSplitedNumber 
    * @Description: 转换成大写
    * @param num
    * @param time
    * @return String
    */
    private static String processSplitedNumber(char[] num, int time) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = num.length - 1; i >= 0; i--) {  
            if (num[i] == '0') {  
                /** 
                 * 当前数字是0.并且是最后一位，直接不读 
                 */  
                if (i == 0) {  
                    continue;  
                }  
                /** 
                 * 当前数字是0，下一个数字不是0，补一个0上去读 
                 */  
                if (num[i - 1] != '0') {  
                    sb.append(numberStringMap.get(Integer.valueOf(num[i] + "")));  
                } else {  
                    continue;  
                }  
            } else {  
                /** 
                 * 如果当前位数不是0，那就正常的读出数字和位 
                 */  
                sb.append(numberStringMap.get(Integer.valueOf(num[i] + "")));  
                sb.append(base10StringMap.get(i + 1));  
            }  
        }  
  
        if (time == 0) {  
        } else if (time == 1) {  
            sb.append("万");  
        } else if (time == 2) {  
            sb.append("亿");  
        }  
        return sb.toString();  
    }

	/**
	 * 将数字转化为大写  
	 * @param num
	 * @return String
	 */
	public static String numToUpper(int num) {   
		String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };  
		char[] str = String.valueOf(num).toCharArray();  
		String rstr = "";  
		for (int i = 0; i < str.length; i++) {  
			rstr = rstr + u[Integer.parseInt(str[i] + "")];  
		 }  
		return rstr;  
	}
    
	static final Writer result = new StringWriter();
	static final PrintWriter printWriter = new PrintWriter(result);
	/**
     * 将异常堆栈转换为字符串
     * @param aThrowable 异常
     * @return String
     */
    public static String getStackTrace(Throwable aThrowable) {
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	}
    
	/** 
	* @Title: revertCharArray 
	* @Description: 将得到的数组倒排,这样做原因是因为读的时候从前往后读， 位数前面的高 
	* @param array
	* @return char[]
	*/
	private char[] revertCharArray(char[] array) {  
        char arrayNew[] = new char[array.length];  
        for (int i = array.length - 1, j = 0; i >= 0 && j < array.length; i--, j++) {  
            arrayNew[j] = array[i];  
        }  
        return arrayNew;  
    } 
	
	/**
	 * 生成min至max之间的随机数
	 * @param min
	 * @param max
	 * @return int
	 */
	public int makeRanNumN(final int min, final int max){
		Random rand = new Random(); 
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
	
	/**
	 * 得到n位长度的随机数
	 * @param n 随机数的长度
	 * @return int 返回 n位的随机整数
	 */
	public static int getRandomNumber(int n){
		int temp = 0;
		int min = (int) Math.pow(10, n-1);
		int max = (int) Math.pow(10, n);
		Random rand = new Random();
		while(true){
			temp = rand.nextInt(max);
			if(temp >= min)
				break;
		}
		return temp;
	}
}
