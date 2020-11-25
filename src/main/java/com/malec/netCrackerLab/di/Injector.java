package com.malec.netCrackerLab.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

public class Injector {
    private static final Map<String, Executable> binds = new HashMap<>();
    private static final Map<String, Object> bindsSingle = new HashMap<>();

    private static boolean isBound = false;

    public static <T> void inject(T target) {
        if (!isBound)
            throw new IllegalStateException("Injector: No methods annotated @Provides found");

        for (Field field : getAllFields(target.getClass()))
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object value = bindsSingle.get(field.getType().getCanonicalName());
                if (value == null)
                    try {
                        value = binds.get(field.getType().getCanonicalName()).execute();
                    } catch (NullPointerException e) {
                        throw new IllegalStateException(
                                "Injector: No provider for " + field.getType()
                                        .getSimpleName() + " in target class " + target.getClass()
                                        .getSimpleName() + " found", e);
                    }

                try {
                    field.set(target, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
    }

    public static void bind(Class<? extends Module> module) {
        Constructor<?> constructor = module.getConstructors()[0];
        try {
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            for (Method method : getAllMethods(module))
                if (method.isAnnotationPresent(Provides.class)) {
                    method.setAccessible(true);

                    if (method.isAnnotationPresent(Singleton.class)) {
                        bindsSingle.put(method.getReturnType().getCanonicalName(),
                                method.invoke(instance, getArgs(method))
                        );
                    } else
                        binds.put(method.getReturnType().getCanonicalName(),
                                new Executable(method, getArgs(method), instance)
                        );
                    isBound = true;
                }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static Method[] getAllMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] allMethods = new Method[methods.length + declaredMethods.length];
        System.arraycopy(methods, 0, allMethods, 0, methods.length);
        System.arraycopy(declaredMethods, 0, allMethods, methods.length, declaredMethods.length);

        return allMethods;
    }

    private static Field[] getAllFields(Class<?> clazz) {
        Field[] fields = clazz.getFields();
        Field[] declaredFields = clazz.getDeclaredFields();
        Field[] allFields = new Field[fields.length + declaredFields.length];
        System.arraycopy(fields, 0, allFields, 0, fields.length);
        System.arraycopy(declaredFields, 0, allFields, fields.length, declaredFields.length);

        return allFields;
    }

    private static Object[] getArgs(Method method) {
        Object[] args = new Object[method.getParameterCount()];
        int i = 0;
        for (Class<?> type : method.getParameterTypes()) {
            Object value = bindsSingle.get(type.getCanonicalName());
            if (value == null)
                value = binds.get(type.getCanonicalName()).execute();
            args[i++] = value;
        }
        return args;
    }
}
