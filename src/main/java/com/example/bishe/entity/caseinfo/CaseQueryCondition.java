package com.example.bishe.entity.caseinfo;
import com.example.bishe.config.base.AbstractPaginationQueryCondition;
import lombok.Data;

@Data
public class CaseQueryCondition extends AbstractPaginationQueryCondition {
    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 餐馆名称
     */
    private String restaurantName;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
