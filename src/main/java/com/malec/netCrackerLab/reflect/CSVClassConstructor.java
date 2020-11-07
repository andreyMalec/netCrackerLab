package com.malec.netCrackerLab.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CSVClassConstructor {
    private static String[] values;

    public static <T> T from(String source, Class<T> jClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        values = valuesFromCSV(source);

        return createClassInstance(jClass);
    }

    private static String[] valuesFromCSV(String source) {
        String[] fields = source.split(",");

        for (int i = 0; i < fields.length; i++)
            fields[i] = fields[i].trim();

        return fields;
    }

    @SuppressWarnings("unchecked")
    private static <T> T createClassInstance(Class<T> type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?>[] types = getFieldsTypes(type);
        Object[] params = makeParams(types).toArray();

        Constructor<?> constructor = type.getConstructor(types);
        constructor.setAccessible(true);
        return (T) constructor.newInstance(params);
    }

    private static Class<?>[] getFieldsTypes(Class<?> jClass) {
        Field[] fields = jClass.getDeclaredFields();
        Class<?>[] types = new Class<?>[fields.length];

        for (int i = 0; i < fields.length; i++)
            types[i] = fields[i].getType();

        return types;
    }

    private static List<Object> makeParams(Class<?>[] types) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        List<Object> params = new ArrayList<>();

        for (Class<?> type : types) {
            if (isBaseType(type)) {
                params.add(createBaseInstance(type, values[0]));
                dropFirstValue();
            } else
                params.add(createClassInstance(type));
        }

        return params;
    }

    private static boolean isBaseType(Class<?> type) {
        return type.isAssignableFrom(String.class) || type.isEnum() || type
                .isPrimitive() || isWrappedType(type);
    }

    private static boolean isWrappedType(Class<?> type) {
        return type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Double.class) || type
                .isAssignableFrom(Float.class) || type.isAssignableFrom(Boolean.class) || type
                .isAssignableFrom(Long.class) || type.isAssignableFrom(Character.class) || type
                .isAssignableFrom(Byte.class) || type.isAssignableFrom(Short.class);
    }

    @SuppressWarnings("unchecked")
    private static Object createBaseInstance(Class<?> type, String value) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        if (type.isPrimitive())
            return createPrimitive(type, value);

        if (type.isEnum())
            return Enum.valueOf((Class<Enum>) type, value.toUpperCase());

        return type.getConstructor(String.class).newInstance(value);
    }

    private static Object createPrimitive(Class<?> primitive, String value) {
        if (boolean.class.equals(primitive))
            return Boolean.parseBoolean(value);
        else if (byte.class.equals(primitive))
            return Byte.parseByte(value);
        else if (char.class.equals(primitive))
            return value.charAt(0);
        else if (short.class.equals(primitive))
            return Short.parseShort(value);
        else if (int.class.equals(primitive))
            return Integer.parseInt(value);
        else if (long.class.equals(primitive))
            return Long.parseLong(value);
        else if (float.class.equals(primitive))
            return Float.parseFloat(value);
        else
            return Double.parseDouble(value);
    }

    private static void dropFirstValue() {
        String[] tmp = new String[values.length - 1];
        System.arraycopy(values, 1, tmp, 0, tmp.length);
        values = tmp;
    }
}
