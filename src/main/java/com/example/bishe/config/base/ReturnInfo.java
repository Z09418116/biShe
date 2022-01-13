package com.example.bishe.config.base;

import lombok.Data;

/**
 * 业务层返回实体
 * @author Matt.Lu
 * @date 2022/01/08
 */
@Data
public class ReturnInfo {

    private Boolean success;
    private String reason;

    public ReturnInfo(Boolean success) {
        this.success = success;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public ReturnInfo() {
        this.success = Boolean.TRUE;
        this.reason = "ok";
    }
}
