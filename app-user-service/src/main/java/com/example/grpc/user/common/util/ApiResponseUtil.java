package com.example.grpc.user.common.util;

import com.example.grpc.user.common.mapper.JacksonUtil;
import com.example.user.gen.grpc.BaseResponse;
import com.google.protobuf.Any;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiResponseUtil {
    public static BaseResponse okGrpc(byte[] data) {
        try {
            return BaseResponse.newBuilder().setStatusCode("200").setStatusMessage("Successfully").setData(Any.parseFrom(data)).build();
        } catch (Exception ex) {
            log.error("Failed to create data: {}", ex.getMessage());
            return BaseResponse.newBuilder().setStatusCode("500").setStatusMessage("Failed to process data").build();
        }
    }

    public static BaseResponse ok(Object data) {
        try {
            return BaseResponse.newBuilder().setStatusCode("200").setStatusMessage("Successfully").setData(Any.parseFrom(JacksonUtil.objectToByte(data))).build();
        } catch (Exception ex) {
            log.error("Failed to create data: {}", ex.getMessage());
            return BaseResponse.newBuilder().setStatusCode("500").setStatusMessage("Failed to process data").build();
        }
    }
}
