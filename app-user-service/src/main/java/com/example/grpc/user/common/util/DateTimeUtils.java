package com.example.grpc.user.common.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class DateTimeUtils {
    public static final String APP_DATE_FORMAT = "dd-MMM-yyyy";
    public static final String ADI_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String TRANSACTION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String ON_BEHALF_DATE_FORMAT = "dd/MM/yyyy";

    public static String formatDate(Date date, String dateFormat) {
        return (date == null || StringUtils.isBlank(dateFormat)) ? "" : new SimpleDateFormat(dateFormat).format(date);
    }

    public static String convertToString(Date date, String dateFormat) {
        return formatDate(date, dateFormat);
    }

    public static String getCurrentDateString(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static Date convertToDate(String dateStr, String dateFormat) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(dateFormat))
            return null;
        try {
            return new SimpleDateFormat(dateFormat).parse(dateStr);
        } catch (ParseException e) {
            log.error("Failed to convertToDate: {}", e.getMessage());
        }
        return null;
    }

    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int minuets) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minuets);

        return calendar.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth() {
        return Date.from(LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static long getDateDifference(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public static boolean validateStringDate(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
        Matcher matcher = pattern.matcher(date);
        return !matcher.matches();
    }

    public static long getCurrentTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static String localDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public static String localDateTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDateTime localDateTime(String localDateTime) {
        try {
            return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        } catch (Exception ignored) {
            return null;
        }
    }
}
