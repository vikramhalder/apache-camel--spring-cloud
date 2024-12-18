package com.example.grpc.user.application.util;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public final class DateTimeMap {
    @Getter
    @AllArgsConstructor
    public enum Format {
        YYYY_MM_DD("yyyy-MM-dd"),
        UTC_TIME("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        DD_MM_YYYY__HH_MM_SS("dd-MM-yyyy HH:mm:ss"),
        YYYY_MM_DD__HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        ;

        private final String val;
    }


    private Format inputFormat;
    final private Object dateObject;

    public DateTimeMap(Object date) {
        this.dateObject = date;
    }

    public static DateTimeMap date(Object date) {
        return new DateTimeMap(date);
    }

    public DateTimeMap format(Format format) {
        this.inputFormat = format;
        return this;
    }

    public String toString() {
        return toString(Format.UTC_TIME);
    }

    public String toString(@Nullable Format dateFormat) {
        try {
            if (dateObject == null || dateFormat == null) {
                return null;
            }
            if (dateObject instanceof Date date) {
                return new SimpleDateFormat(dateFormat.getVal()).format(date);
            } else if (dateObject instanceof Instant instant) {
                return new SimpleDateFormat(dateFormat.getVal()).format(instant);
            } else if (dateObject instanceof LocalDate localDate) {
                return localDate.format(DateTimeFormatter.ofPattern(dateFormat.getVal()));
            } else if (dateObject instanceof LocalDateTime localDateTime) {
                return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat.getVal()));
            } else if (dateObject instanceof OffsetDateTime offsetDateTime) {
                return offsetDateTime.format(DateTimeFormatter.ofPattern(dateFormat.getVal()));
            } else if (dateObject instanceof ZonedDateTime zonedDateTime) {
                return zonedDateTime.format(DateTimeFormatter.ofPattern(dateFormat.getVal()));
            }
            if (dateObject instanceof String str) {
                if (inputFormat != null) {
                    Date date = new SimpleDateFormat(inputFormat.getVal()).parse(str);
                    return new SimpleDateFormat(dateFormat.getVal()).format(date);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to parse date: {}", ex.getMessage());
        }
        return null;
    }

    public Date toDate() {
        return toDate(Format.YYYY_MM_DD__HH_MM_SS);
    }

    public Date toDate(@Nullable Format dateFormat) {
        try {
            if (dateObject == null || dateFormat == null) {
                return null;
            }
            if (dateObject instanceof Date date) {
                return date;
            } else if (dateObject instanceof Instant instant) {
                return Date.from(instant);
            } else if (dateObject instanceof LocalDate localDate) {
                return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else if (dateObject instanceof LocalDateTime localDateTime) {
                return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            } else if (dateObject instanceof OffsetDateTime offsetDateTime) {
                return Date.from(offsetDateTime.toInstant());
            } else if (dateObject instanceof ZonedDateTime zonedDateTime) {
                return Date.from(zonedDateTime.toInstant());
            }
            if (dateObject instanceof String str) {
                if (inputFormat != null) {
                    return new SimpleDateFormat(inputFormat.getVal()).parse(str);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to parse date: {}", ex.getMessage());
        }
        return null;
    }
}
