package com.example.blogchipo.response;

import lombok.Data;

import java.io.Serializable;


@Data
public class Response<T> implements Serializable {
    private String message;
    private int statusCode;
    private T obj;
    public Response(String message, int statusCode, T obj) {
        this.message = message;
        this.statusCode = statusCode;
        this.obj = obj;
    }
}
