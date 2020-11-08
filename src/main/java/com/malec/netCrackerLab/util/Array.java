package com.malec.netCrackerLab.util;

import java.util.List;

public class Array {
    public static String join(List<Object> objects, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.size(); i++) {
            sb.append(objects.get(i));
            if (i != objects.size() - 1)
                sb.append(separator);
        }

        return sb.toString();
    }

    public static String join(Object[] objects, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i]);
            if (i != objects.length - 1)
                sb.append(separator);
        }

        return sb.toString();
    }
}
