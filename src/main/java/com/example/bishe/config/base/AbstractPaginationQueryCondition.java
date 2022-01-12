package com.example.bishe.config.base;

import lombok.Data;

@Data
public class AbstractPaginationQueryCondition {
    private Double start;
    private Double limit;
    private Double page;
}
