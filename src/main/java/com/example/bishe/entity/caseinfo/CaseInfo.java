package com.example.bishe.entity.caseinfo;

import lombok.Data;

import java.util.List;

@Data
public class CaseInfo {
    /**
     * 案件id
     */
    private Long id;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 餐馆名称
     */
    private String restaurantName;

    /**
     * 餐馆主人名称
     */
    private String legalPersonName;

    /**
     * 餐馆主人联系电话
     */
    private String legalPersonPhone;

    /**
     * 餐馆地址
     */
    private String address;

    /**
     * 执法行动领导
     */
    private String lawEnforcerLeader;

    /**
     * 执法队员
     */
    private String lawEnforcer;

    /**
     * 执法时间
     */
    private String lawEnforcementTime;

    /**
     * 检查结果(0-良好无问题 1-口头告诫 2-书面警告 3-停业整改)
     */
    private String inspectResult;

    /**
     * 是否通过
     */
    private String isPass;

    /**
     * 整改意见
     */
    private String rectificationOpinions;

    /**
     * 案件状态  0-未审核 1-通过 2-不通过
     */
    private String caseState;

    /**
     * 是否删除该用案件  1-不删除   0-删除
     */
    private String deletedFlag;

    /**
     * 是否冻结该案件  1-正常使用   0-冻结
     */
    private String freezedFlag;

    /**
     * 案件附件集合
     */
    private List<CaseInfoEnclosure> caseInfoEnclosureList;


}
