package com.imooc.o2o;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zy on 2019/1/17
 */
public class CommonTest {

    @Test
    public void test02() {
        String fileName = "jdbc.properties";
        String encoding = "utf-8";
        InputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
            }
            properties = new Properties();
            properties.load(new InputStreamReader(inputStream, encoding));
            String key = properties.getProperty("jdbc.driver");
            System.out.println(key);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        }
        finally {
            if (inputStream != null) try {inputStream.close();}
            catch (IOException e) {e.printStackTrace();}
        }

    }

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
