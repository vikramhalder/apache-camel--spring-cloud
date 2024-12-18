package com.example.grpc.user.application.util;

public class StringUtils {
    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
