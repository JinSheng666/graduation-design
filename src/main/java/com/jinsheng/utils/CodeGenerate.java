package com.jinsheng.utils;

import java.util.Random;

public class CodeGenerate {

    /*生成随机编码*/
    public static String generate() {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                //首位不能为0且数字取值区间为 [1,9]
                str += (random.nextInt(9) + 1);
            } else {
                //其余位的数字的取值区间为 [0,9]
                str += random.nextInt(10);
            }
        }
        return str;
    }
}
