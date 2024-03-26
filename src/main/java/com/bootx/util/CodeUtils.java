package com.bootx.util;

import java.util.Random;

/**
 * @author black
 */
public final class CodeUtils {

    private static final Random RANDOM = new Random();


    public static String getCode(Integer length){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10));
        }
        return code.toString();

    }


}
