package com.shangguan.mybatis1.result;

public class Result {

    public static final int Integer = 0;
    public static final int String = 1;
    public static final int JsonObject = 2;
    public static final int JsonArray = 3;

    private int resultCode = -1;
    private String msg;
    private Object data;

    /* private T dataObj; */

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result [resultCode=" + resultCode + ", msg=" + msg + ", data=" + data + "]";
    }

}
