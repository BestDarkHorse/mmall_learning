package com.mmall.test;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalTest {

    @Test
    public void test1() {
        System.out.println(0.005 + 0.001);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);
    }

    @Test
    public void test3() {
        BigDecimal b1 = new BigDecimal("0.005");
        BigDecimal b2 = new BigDecimal("0.001");
        System.out.println(b1.add(b2));
    }

    @Test
    public void test4() {
        String a = "1";
        String b = "1,2,3";

        List<String> stringA = Splitter.on(",").splitToList(a);
        List<String> stringB = Splitter.on(",").splitToList(b);
        System.out.println("testA: " + stringA);
        System.out.println("testB: " + stringB);
    }

}
