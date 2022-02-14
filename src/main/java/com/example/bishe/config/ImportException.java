package com.example.bishe.config;

import lombok.Getter;

/**
 * @ClassName ImportException
 * @Description 自定义导入异常
 * @Author Chris.Liu
 * @DATE 2021/9/23 15:42
 */
@Getter
public final class ImportException extends RuntimeException {

    public ImportException(Throwable throwable) {
        super(throwable);
    }

    public ImportException(String message) {
        super(message);
    }
}
