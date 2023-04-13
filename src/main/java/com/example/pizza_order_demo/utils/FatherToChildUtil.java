package com.example.pizza_order_demo.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FatherToChildUtil {
    public static void fatherToChild(Object father, Object child) throws InvocationTargetException {

        if (!(child.getClass().getSuperclass() == father.getClass())) {
            return;
        }
        Class fatherClass = father.getClass();
        Field ff[] = fatherClass.getDeclaredFields();
        for (Field f : ff) {
            //过滤掉字段serialVersionUID
            if (!StringUtils.equals("serialVersionUID", f.getName())) {
                //取出每一个属性，如deleteDate
                Class type = f.getType();
                try {
                    Method fm = fatherClass.getMethod("get" + upperHeadChar(f.getName()));//方法getDeleteDate
                    Object obj = fm.invoke(father);//取出属性值
                    Method cm = child.getClass().getDeclaredMethod("set" + upperHeadChar(f.getName()), type);
                    Object result = cm.invoke(child, obj);
                } catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {

                }
            }

        }
    }

    private static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }
}
