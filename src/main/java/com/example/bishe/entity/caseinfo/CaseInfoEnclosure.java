package com.example.bishe.entity.caseinfo;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CaseInfoEnclosure {
    /**
     * 参观指导管理ID
     */
    private Long caseId;

    /**
     * 附件名称
     */
    private String fileName;

    /**
     * 路径
     */
    private String fileUrl;
}
