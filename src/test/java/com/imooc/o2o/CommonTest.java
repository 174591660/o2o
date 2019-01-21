package com.imooc.o2o;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zy on 2019/1/17
 */
public class CommonTest {

    @Test
    public void test01() {
        String s = "LVIII";

        int[] tagVal = new int[256];
        tagVal['I'] = 1;
        tagVal['V'] = 5;
        tagVal['X'] = 10;
        tagVal['C'] = 100;
        tagVal['M'] = 1000;
        tagVal['L'] = 50;
        tagVal['D'] = 500;
        int val = 0;
        for(int i = 0; i < s.length(); i++){
            if(i+1 >= s.length() || tagVal[s.charAt(i+1)] <= tagVal[s.charAt(i)])
                val += tagVal[s.charAt(i)];
            else
                val -= tagVal[s.charAt(i)];
        }
        System.out.println(val);

    }

}
