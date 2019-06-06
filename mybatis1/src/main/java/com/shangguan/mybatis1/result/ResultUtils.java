package com.shangguan.mybatis1.result;

public class ResultUtils {

    private static Result PARAM_ERROR = createResult(1, 0, Result.JsonObject, "参数异常", "");
    private static Result LACK_PARAMS_ERROR = createResult(2, 0, Result.JsonObject, "缺少必要参数", "");
    private static Result NO_AUTHORITY = createResult(-200, 0, Result.JsonObject, "没有请求权限", "");
    private static Result UNKNOWN = createResult(0, 0, Result.JsonObject, "未知错误", "");
    private static Result SESSION_TIMEOUT = createResult(5, 1, Result.JsonObject, "session过期", "");
    private static Result COMMON_SUCCESS = createResult(-1, 1, Result.JsonObject, "操作成功", "");

    public static Result success() {
        return COMMON_SUCCESS;
    }

    public static Result success(Object data) {
        return createResult(-1, 1, Result.JsonObject, data);
    }

    public static Result result(int resultCode, String msg, Object data) {
        return createResult(resultCode, 1, Result.JsonObject, msg, data);
    }

    public static Result resultList(int resultCode, String msg, Object data) {
        return createResult(resultCode, 1, Result.JsonArray, msg, data);
    }

    public static Result lackParamsError() {
        return LACK_PARAMS_ERROR;
    }

    public static Result paramError() {
        return PARAM_ERROR;
    }

    public static Result sessionTimeoutError() {
        return SESSION_TIMEOUT;
    }

    public static Result noAuthority() {
        return NO_AUTHORITY;
    }

    public static Result unknown() {
        return UNKNOWN;
    }

    public static Result detailMsgError(String msg) {
        return createResult(0, 1, Result.JsonObject, msg, "");
    }

    private static Result createResult(int resultCode, int valid, int dataType, String msg, Object data) {
        Result r = new Result();
        r.setResultCode(resultCode);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    private static Result createResult(int resultCode, int valid, int dataType, Object data) {
        return createResult(resultCode, valid, dataType, "", data);
    }
}
