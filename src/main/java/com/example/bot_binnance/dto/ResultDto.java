package com.example.bot_binnance.dto;

public class ResultDto<T> {

    private int code;     // Mã trạng thái HTTP hoặc mã phản hồi
    private String msg;   // Thông báo phản hồi
    private T data;       // Dữ liệu tổng quát

    public ResultDto() {
    }

    public ResultDto(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
