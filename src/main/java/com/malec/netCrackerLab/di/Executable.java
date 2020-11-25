package com.malec.netCrackerLab.di;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Executable {
    private Method method;
    private Object[] args;
    private Object target;

    public Executable(Method method, Object[] args, Object target) {
        this.method = method;
        this.args = args;
        this.target = target;
    }

    public Object execute() {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
