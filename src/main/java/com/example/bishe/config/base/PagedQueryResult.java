package com.example.bishe.config.base;

import lombok.Data;

import java.util.List;

@Data
public class PagedQueryResult<T> {
    private Integer totalCount;
    private List<T> results;
}
