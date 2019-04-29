package com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by  on 2018/11/27.
 */

public class SignUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 创建 Sign
     * 规则：将所有请求参数按照ASCII升序排序,
     *      接着用MD5加密处理并将得到的值全部大写,
     *      再接着将得到的值加上请求头中的时间戳,
     *      将得到的值用SHA1加密
     */
    public static String createSign(Map<String, String> params, String timeStamp){
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key_value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value =  entry.getValue().trim();
            if (!TextUtils.isEmpty(value))
                sb.append(key).append("=").append(value).append("&");
        }
        sb.replace(sb.length()-1,sb.length(),"");
        Log.e("MD5",sb.toString());
        sb.toString();
        String signMD5 = encodeMD5(sb.toString()).toUpperCase();
        return signMD5;
    }

    /**
     * 校验签名
     * @param params 请求参数
     * @param sign 请求头中的签名
     * @return
     */
    public static boolean checkSign(Map<String,String> params,String timeStamp,String sign) {
        if(TextUtils.isEmpty(sign)){
            return false;
        }
        String verifySign = createSign(params,timeStamp);
        return sign.equals(verifySign) ? true : false;
    }

    /**
     * 计算并获取md5值
     * @param requestBody
     * @return
     */
    public static String encodeMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    /**
     * 在md5基础上+请求头中的时间戳
     * 使用sha1加密
     * @param nonce
     * @param curTime
     * @return
     */
    public static String encodeSHA1(String nonce, String curTime) {
        return encode("sha1",  nonce + curTime);
    }

    public static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把密文转换成十六进制的字符串形式
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}
