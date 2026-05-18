package com.redis_product_app.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors; // optional

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
