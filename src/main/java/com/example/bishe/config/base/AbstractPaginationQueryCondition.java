package com.example.bishe.config.base;

import lombok.Data;

@Data
public class AbstractPaginationQueryCondition {
    private Integer start;
    private Integer limit;
    private Integer page;
}
