package com.example.backend.common;

public class Result {
    private int code;
    private String message;
    private Object data;
    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(0, "success", null);
    }
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result error(){
        return new Result(500, "error", null);
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
