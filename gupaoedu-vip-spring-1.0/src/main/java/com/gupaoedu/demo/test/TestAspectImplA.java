package com.gupaoedu.demo.test;

public class TestAspectImplA implements TestAspect {
    @Override
    public String test(String sr) {
        System.out.println("run TestAspectImplA.test " + sr);
        return sr;
    }
}
