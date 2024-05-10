package com.thelastofus.cloudstorage.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class TimeUtil {

    public static DateTimeFormatter getTimePattern() {
        return DateTimeFormatter.ofPattern("EEE, MMM dd yyyy HH:mm '(GMT+2)'", Locale.ENGLISH);
    }
}
