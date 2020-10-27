package com.malec.netCrackerLab.util;

import java.util.Calendar;

public class DateConverter {
    public static int getAge(long birthday) {
        long now = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(now - birthday);
        return c.get(Calendar.YEAR) - 1970;
    }
}
