package com.example.voucher.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDate convert(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static boolean dateIsPassed(LocalDate date) {
        return  date.compareTo(LocalDate.now()) > 0 ? true : false;
    }
}
