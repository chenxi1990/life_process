package com.gupaoedu.demo.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyAspect implements InvocationHandler {
    Object instance;

    public Object aspect(Object instance) {
        this.instance = instance;
        return Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(),this);
    }

    public void before(Method method, Object[] args) {
        System.out.println("~~~~ before: " + method.getName() + " " + args);
    }

    public void after(Object object) {
        System.out.println("~~~~ after: " + object);
    }

    public static void init() {
        Field[] fields= MyAspect.class.getDeclaredFields();

        for(int i=0;i<fields.length;i++){
            MyAspect myAspect = new MyAspect();
            Aspect aspects = fields[i].getType().getAnnotation(Aspect.class);
            if(aspects != null) {
                fields[i].setAccessible(true);
                try {
                    fields[i].set(myAspect, new MyAspect().aspect(fields[i].get(myAspect)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;
        before(method, args);
        result = method.invoke(instance, args);
        after(result);
        return null;
    }

    public static TestAspect testAspect = new TestAspectImpl();
    public static TestAspect testAspectA = new TestAspectImplA();;

    public static void main(String []args) {
        init();
        testAspect.test("I'm TestAspectImpl");
        testAspectA.test("I'm TestAspectImplA");
    }
}
