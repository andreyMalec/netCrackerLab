package com.malec.netCrackerLab.di;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class Request {
    public final Class<?> type;
    public final Object[] genericTypeArguments;

    public Request(Field field) {
        this.type = field.getType();
        this.genericTypeArguments = getGenericTypeArguments(field.getGenericType());
    }

    public Request(Parameter parameter) {
        this.type = parameter.getType();
        this.genericTypeArguments = getGenericTypeArguments(parameter.getParameterizedType());
    }

    private Object[] getGenericTypeArguments(Type type) {
        if (type instanceof ParameterizedType)
            return Arrays.stream(((ParameterizedType) type).getActualTypeArguments()).map(Type::getTypeName)
                    .toArray();

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Request request = (Request) o;
        return type.equals(request.type) && Arrays.equals(genericTypeArguments, request.genericTypeArguments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type);
        result = 31 * result + Arrays.hashCode(genericTypeArguments);
        return result;
    }
}
