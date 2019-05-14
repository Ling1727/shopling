package com.example.shopling.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hasee on 2019/4/19.
 */

public class Verify {
private static final int IS_CELL_PHONE_NUMBER=0;
    public static boolean isLegal(String verify,int type){
        boolean flag = false;
        switch (type) {
            case IS_CELL_PHONE_NUMBER:
                flag=isCellPhoneNumber(verify);
                break;
        }
        return flag;
    }

    //验证是否为手机号码
    public static boolean isCellPhoneNumber(String number) {
        boolean flag = false;
        try {
            // 13********* ,15********,18*********
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
