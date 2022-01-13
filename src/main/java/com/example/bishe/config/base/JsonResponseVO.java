package com.example.bishe.config.base;

public class JsonResponseVO {
    private Boolean success;
    private String reason;

    public JsonResponseVO(Boolean success) {
        this.success = success;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public JsonResponseVO() {
        this.success = Boolean.TRUE;
        this.reason = "ok";
    }
}
