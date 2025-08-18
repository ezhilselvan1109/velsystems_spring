package com.velsystems.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class api {
    @Data
    @AllArgsConstructor
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public static <T> ApiResponse<T> success(String message, T data) {
            return new ApiResponse<>(true, message, data);
        }

        public static <T> ApiResponse<T> failure(String message) {
            return (ApiResponse<T>) new ApiResponse<>(false, message, null);
        }
    }
}
