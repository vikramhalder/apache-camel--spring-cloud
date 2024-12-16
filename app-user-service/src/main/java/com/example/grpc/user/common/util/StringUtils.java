package com.example.grpc.user.common.util;

public class StringUtils {
    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
