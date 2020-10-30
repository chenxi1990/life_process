package com.gupaoedu.demo.test;
@Aspect
public class TestAspectImpl implements TestAspect {
    @Override
    public String test(String str) {
        System.out.println("run TestAspectImpl.test " + str);
        return str;
    }
}
