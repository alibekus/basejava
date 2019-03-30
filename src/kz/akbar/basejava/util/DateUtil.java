package kz.akbar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.now();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }
}
