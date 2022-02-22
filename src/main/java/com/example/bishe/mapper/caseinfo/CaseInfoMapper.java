package com.example.bishe.mapper.caseinfo;

import com.example.bishe.entity.caseinfo.CaseInfo;
import com.example.bishe.entity.caseinfo.CaseInfoEnclosure;
import com.example.bishe.entity.caseinfo.CaseQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CaseInfoMapper {
    /**
     * 根据条件查询案件信息数量
     *
     * @param caseQueryCondition 查询条件实体类
     * @return 符合查询条件的记录数量
     */
    Integer queryCaseInfoCount(@Param("caseQueryCondition") CaseQueryCondition caseQueryCondition);

    /**
     * 根据条件查询案件信息
     *
     * @param caseQueryCondition 查询条件实体类
     * @return  案件信息实体类集合
     */
    List<CaseInfo> queryCaseInfo(@Param("caseQueryCondition") CaseQueryCondition caseQueryCondition);

    /**
     * 判断案件是否存在
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgeCaseInfoExistence(Long id);

    /**
     * 添加(修改)案件信息
     *
     * @param caseInfo 案件信息实体类
     * @return  是否成功
     */
    Boolean addCaseInfo(@Param("caseInfo") CaseInfo caseInfo);

    /**
     * 删除案件信息
     *
     * @param id 案件id
     * @return  是否成功
     */
    Boolean deleteCaseInfo(Long id);

    /**
     * 冻结案件信息
     *
     * @param id 案件id
     * @return  是否成功
     */
    Boolean freezeCaseInfo(Long id);

    /**
     * 冻结案件信息
     *
     * @param id 案件id
     * @return  是否成功
     */
    Boolean changeCaseInfoStatus(@Param("id") Long id, @Param("state") Integer state);

    /**
     * 修改案件信息
     *
     * @param caseInfo 案件信息实体类,id 案件id
     * @return  是否成功
     */
    Boolean updateCaseInfo(@Param("caseInfo") CaseInfo caseInfo, @Param("id") Long id);

    /**
     * 导入案件附件信息
     *
     * @param caseInfoEnclosureList 案件附件信息列表,caseInfoId 案件id
     * @return  数量
     */
    Integer addCaseInfoEnclosure(@Param("caseInfoEnclosureList") List<CaseInfoEnclosure> caseInfoEnclosureList, @Param("caseInfoId") Long caseInfoId);

    /**
     * 清除所有关联图片附件
     *
     * @param id 案件信息实体类
     * @return  修改数量
     */
    Integer cleanCaseInfoEnclosure(Long id);



}
