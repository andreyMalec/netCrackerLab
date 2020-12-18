package com.malec.netCrackerLab.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

public class Injector {
    private static final Map<String, Object> cachedDependencies = new HashMap<>();
    private static final List<Method> providers = new ArrayList<>();

    private static Class<?> dependencyProvider = null;
    private static Object dependencyProviderInstance = null;

    public static void init(Class<?> dependencyProvider) {
        Injector.dependencyProvider = dependencyProvider;
        dependencyProviderInstance = instanceOrNull(dependencyProvider);
        providers.addAll(getProviders(dependencyProvider));
    }

    private static Object instanceOrNull(Class<?> target) {
        Constructor<?> constructor = target.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Object instance = null;
        try {
            instance = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static Set<Method> getProviders(Class<?> target) {
        Set<Method> methods = new HashSet<>();
        for (Method method : target.getDeclaredMethods())
            if (method.isAnnotationPresent(Provides.class))
                methods.add(method);
        return methods;
    }

    public static <T> void inject(T target) {
        if (dependencyProviderInstance == null)
            throw new IllegalStateException("You must call Injector.init(...) before inject");

        for (Field field : getAllFields(target.getClass()))
            if (field.isAnnotationPresent(Inject.class)) {
                Request request = new Request(field);

                Object value = provide(request);
                if (value == null)
                    throw new IllegalStateException(
                            "Injector: No provider for " + field.getGenericType().getTypeName() +
                                    " in class " + target.getClass().getSimpleName() + " from target " +
                                    dependencyProvider.getSimpleName() + " found");
                set(target, field, value);
            }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> allFields = new ArrayList<>(Arrays.asList(declaredFields));
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != Object.class) {
            Field[] fields = superClass.getDeclaredFields();
            allFields.addAll(Arrays.asList(fields));
            superClass = superClass.getSuperclass();
        }
        return allFields;
    }

    private static Object provide(Request request) {
        for (Method provider : providers)
            if (typeEquals(request, provider))
                if (provider.isAnnotationPresent(Singleton.class))
                    return invokeAndCache(provider);
                else
                    return invoke(provider);

        return null;
    }

    private static boolean typeEquals(Request request, Method provider) {
        Class<?> providerType = provider.getReturnType();

        if (request.genericTypeArguments == null)
            return providerType.isAssignableFrom(request.type);

        Type generic = provider.getGenericReturnType();
        if (generic instanceof ParameterizedType) {
            Object[] arguments = Arrays.stream(((ParameterizedType) generic).getActualTypeArguments())
                    .map(Type::getTypeName).toArray();
            return Set.of(request.genericTypeArguments).equals(Set.of(arguments));
        }

        return false;
    }

    private static Object invokeAndCache(Method provider) {
        Object value = cachedDependencies.get(provider.getGenericReturnType().getTypeName());
        if (value == null) {
            value = invoke(provider);
            cachedDependencies.put(provider.getGenericReturnType().getTypeName(), value);
        }

        return value;
    }

    private static Object invoke(Method provider) {
        if (provider.getParameterCount() == 0)
            return invokeOrNull(dependencyProviderInstance, provider);
        else {
            Object[] args = new Object[provider.getParameterCount()];
            int i = 0;
            for (Parameter parameter : provider.getParameters())
                args[i++] = provide(new Request(parameter));

            return invokeOrNull(dependencyProviderInstance, provider, args);
        }
    }

    private static Object invokeOrNull(Object instance, Method method, Object[] args) {
        Object value = null;
        method.setAccessible(true);
        try {
            value = method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static Object invokeOrNull(Object instance, Method method) {
        return invokeOrNull(instance, method, null);
    }

    private static void set(Object target, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
