package com.example.apollochallenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private T data;
    private String errorMessage;

    public static <T> BaseResponse<T> toSuccess(T data) {
        return new BaseResponse<>(true, data, null);
    }
    public static <T> BaseResponse<T> toError(T data, String error) {
        return new BaseResponse<>(false, data, error);
    }
}
