package com.example.bishe.service.caseinfo;

import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.caseinfo.CaseInfo;
import com.example.bishe.entity.caseinfo.CaseQueryCondition;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.entity.restaurant.RestaurantQueryCondition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CaseInfoService {
    /**
     * 根据条件查询查询案件信息
     *
     * @param caseQueryCondition 查询条件实体类
     * @return  案件信息实体类集合
     */
    PagedQueryResult<CaseInfo> queryCaseInfo(CaseQueryCondition caseQueryCondition);

    /**
     * 添加(修改)案件信息
     *
     * @param caseInfo 案件信息实体类
     * @return  是否成功
     */
    ReturnInfo addCaseInfo(CaseInfo caseInfo,MultipartFile[] attachFile);

    /**
     * 删除案件信息
     *
     * @param id 案件id
     * @return  是否成功
     */
    ReturnInfo deleteCaseInfo(Long id);

    /**
     * 冻结案件信息
     *
     * @param id 案件id
     * @return  是否成功
     */
    ReturnInfo freezeCaseInfo(Long id);

    /**
     * 修改案件状态
     *
     * @param id 案件id state 状态
     * @return  是否成功
     */
    ReturnInfo changeCaseInfoStatus(Long id,Integer state);



    /**
     * 上传案件信息附件
     *
     * @param attachFile 案件信息列表
     * @return  是否成功
     */
    ReturnInfo uploadEnclosure(MultipartFile attachFile);
}
