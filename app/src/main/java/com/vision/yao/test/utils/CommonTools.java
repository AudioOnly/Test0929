package com.vision.yao.test.utils;

import java.util.regex.Pattern;

/**
 * Created by Ronnie on 2016/8/2.
 * E_mail：ruining.yao@visionchina.cn
 */
public class CommonTools {


    /**
     * 用正则表达式判断一个字符串是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^\\d+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断手机号是否正确
     *
     * @param phoneNum
     * @return
     */
    public static boolean checkPhoneNumFormat(String phoneNum) {
        if (phoneNum != null && !"".equals(phoneNum)
                && phoneNum.getBytes().length == 11
                && isNumeric(phoneNum)) {
            return true;
        } else {
            return false;
        }
    }
}
