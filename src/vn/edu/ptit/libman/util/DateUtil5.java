package vn.edu.ptit.libman.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Lớp tiện ích DateUtil5 - Xử lý chuyển đổi và định dạng ngày tháng.
 * So khớp với Sơ đồ gói và kiến trúc hệ thống (Hậu tố 5 - B23DCCN015).
 */
public class DateUtil5 {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    private static final String DB_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DB_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(DATETIME_PATTERN).format(date);
    }

    public static String toDbDateString(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat(DB_DATE_PATTERN).format(date);
    }

    public static String toDbDateTimeString(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat(DB_DATETIME_PATTERN).format(date);
    }

    public static Date parseDate(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(text.trim());
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat(DB_DATE_PATTERN).parse(text.trim());
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    public static Date addDays(Date baseDate, int days) {
        if (baseDate == null) baseDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}

