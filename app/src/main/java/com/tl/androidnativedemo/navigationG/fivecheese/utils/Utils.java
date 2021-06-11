package com.tl.androidnativedemo.navigationG.fivecheese.utils;

import com.tl.androidnativedemo.navigationG.fivecheese.model.UserVo;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by tianlin on 2017/9/30.
 * Tel : 15071485690
 * QQ : 953108373
 */

public class Utils
{

    final static String[] genders = new String[]{"male", "female"};
    public static UserVo makeAUser()
    {
        Random random = new Random(System.currentTimeMillis());
        UserVo userVo = new UserVo();
        userVo.id = getNumber(3);
        userVo.username = "U" + userVo.id;
        userVo.gender = genders[random.nextInt(genders.length)];
        return userVo;
    }
    public static String getNumber(int numberLength) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder str_number = new StringBuilder(numberLength);
        for (int i = 1; i <= numberLength; i++) {
            str_number.append(secureRandom.nextInt(10));
        }
        return str_number.toString();
    }

}
