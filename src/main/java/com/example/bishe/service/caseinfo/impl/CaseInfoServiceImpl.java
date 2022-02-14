package com.example.bishe.service.caseinfo.impl;

import com.example.bishe.config.Constants;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.caseinfo.CaseInfo;
import com.example.bishe.entity.caseinfo.CaseInfoEnclosure;
import com.example.bishe.entity.caseinfo.CaseQueryCondition;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.mapper.caseinfo.CaseInfoMapper;
import com.example.bishe.mapper.restaurant.RestaurantMapper;
import com.example.bishe.service.caseinfo.CaseInfoService;
import com.example.bishe.service.restaurant.RestaurantService;
import com.example.bishe.service.restaurant.impl.RestaurantServiceImpl;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.bishe.config.Constants.*;
import static com.example.bishe.config.FileHelper.saveFile;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class CaseInfoServiceImpl implements CaseInfoService {


    private static final Logger LOGGER = LoggerFactory.getLogger(CaseInfoServiceImpl.class);

    private CaseInfoMapper caseInfoMapper;
    private final Constants constants;

    @Autowired
    public CaseInfoServiceImpl(CaseInfoMapper caseInfoMapper,Constants constants) {
        this.caseInfoMapper = caseInfoMapper;
        this.constants = constants;
    }

    /**
     * 案件信息-查询案件信息
     *
     * @param caseQueryCondition 案件信息查询实体类
     * @return 案件信息实体类列表
     */
    @Override
    public PagedQueryResult<CaseInfo> queryCaseInfo(CaseQueryCondition caseQueryCondition) {
        LOGGER.info("开始根据条件查询案件信息-CaseInfoServiceImpl");

        final PagedQueryResult<CaseInfo> pagedQueryResult = new PagedQueryResult<>();

        //查询符合条件的数量
        final Integer count = caseInfoMapper.queryCaseInfoCount(caseQueryCondition);

        //有记录则查询
        if (count != null && !NUM_ZERO.equals(count)) {
            pagedQueryResult.setTotalCount(count);
            pagedQueryResult.setResults(caseInfoMapper.queryCaseInfo(caseQueryCondition));
        } else {
            pagedQueryResult.setTotalCount(NUM_ZERO);
            pagedQueryResult.setResults(Lists.newArrayList());
        }
        return pagedQueryResult;
    }

    /**
     * 案件信息-新增（修改）案件信息
     *
     * @param caseInfo 案件信息实体类
     * @return 是否成功新增或修改
     */
    @Override
    public ReturnInfo addCaseInfo(CaseInfo caseInfo,MultipartFile[] attachFile) {

        LOGGER.info("案件管理附件上传-impl");
        final Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        List<String> reason = new ArrayList<>();
        List<CaseInfoEnclosure> caseInfoEnclosureList = new ArrayList<>();
        CaseInfoEnclosure caseInfoEnclosure =new CaseInfoEnclosure();
        try {
            for (MultipartFile file : attachFile) {
                caseInfoEnclosure.setFileName(file.getOriginalFilename());
                final String uri = saveFile(file, constants.getRootPath(), CASE_INFO_GUIDANCE);
                reason.add(uri);
                caseInfoEnclosure.setFileUrl(uri);
                caseInfoEnclosure.setCaseId(caseInfo.getId());
                caseInfoEnclosureList.add(caseInfoEnclosure);
            }
        } catch (IOException e) {
            LOGGER.error("上传文件失败:{}", e.getMessage());
            result.put(RESPONSE_SUCCESS_PARAM, false);
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        if (NUM_ONE.equals(caseInfoMapper.judgeCaseInfoExistence(caseInfo.getId()))){
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("案件信息-修改案件信息-impl,id:{}");
            }
            //修改案件详细信息
            returnInfo.setSuccess(caseInfoMapper.updateCaseInfo(caseInfo,caseInfo.getId()));
            //修改附件
            if (caseInfo.getCaseInfoEnclosureList() != null && caseInfo.getCaseInfoEnclosureList().size() > NUM_ZERO) {
                //清除所有关联图片附件
                caseInfoMapper.cleanCaseInfoEnclosure(caseInfo.getId());
                //赋值
                caseInfo.setCaseInfoEnclosureList(caseInfoEnclosureList);
                caseInfoMapper.addCaseInfoEnclosure(caseInfo.getCaseInfoEnclosureList(), caseInfo.getId());
            } else {
                caseInfoMapper.cleanCaseInfoEnclosure(caseInfo.getId());
            }
        }else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("案件信息-添加案件信息-impl,id:{}");
            }
            //添加案件详细信息
            returnInfo.setSuccess(caseInfoMapper.addCaseInfo(caseInfo));
            //获取id作为caseId
            for (CaseInfoEnclosure enclosure : caseInfoEnclosureList) {
                enclosure.setCaseId(caseInfo.getId());
            }
            //赋值
            caseInfo.setCaseInfoEnclosureList(caseInfoEnclosureList);
            //新增附件
            if (caseInfo.getCaseInfoEnclosureList()!=null && caseInfo.getCaseInfoEnclosureList().size() > NUM_ZERO) {
                caseInfoMapper.addCaseInfoEnclosure(caseInfo.getCaseInfoEnclosureList(), caseInfo.getId());
            }
        }
        return returnInfo;
    }

    /**
     * 新增参观指导管理附件
     *
     * @param caseInfo 参观指导管理实体类
     * @return 返回执行结果  success-true 成功 success-false 失败  reason 错误信息
     */
    private ReturnInfo saveAttachment(CaseInfo caseInfo) {
        final ReturnInfo returnInfo = new ReturnInfo();
        LOGGER.info("开始新增案件记录附件-service");
        //图片附件集合
        final List<CaseInfoEnclosure> caseInfoEnclosureList = caseInfo.getCaseInfoEnclosureList();

        //添加创建人与关联案件ID
        caseInfo.getCaseInfoEnclosureList().forEach(caseInfoEnclosure -> {
            caseInfoEnclosure.setCaseId(caseInfo.getId());
        });
        //新增图片
        final Integer count = caseInfoMapper.addCaseInfoEnclosure(caseInfoEnclosureList, caseInfo.getId());
        if (NUM_ZERO.equals(count)) {
            returnInfo.setSuccess(FALSE);
        } else {
            returnInfo.setSuccess(TRUE);
        }
        return returnInfo;
    }





    /**
     * 案件信息-删除案件信息
     *
     * @param id 案件id
     * @return 是否成功删除
     */
    @Override
    public ReturnInfo deleteCaseInfo(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("案件信息-删除案件信息-impl,id:{}",id);
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        //删除案件信息
        returnInfo.setSuccess(caseInfoMapper.deleteCaseInfo(id));
        return returnInfo;
    }

    /**
     * 案件信息-冻结案件信息
     *
     * @param id 案件id
     * @return 是否成功冻结
     */
    @Override
    public ReturnInfo freezeCaseInfo(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("案件信息-冻结案件信息-impl,id:{}",id);
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        //冻结案件信息
        returnInfo.setSuccess(caseInfoMapper.freezeCaseInfo(id));
        return returnInfo;
    }

    @Override
    public Boolean modifyCaseInfo(Long id, Integer state) {
        return null;
    }

    @Override
    public ReturnInfo importCaseInfo(List<RestaurantInfo> restaurantInfoList) {
        return null;
    }

    @Override
    public ReturnInfo uploadEnclosure(MultipartFile attachFile) {
        LOGGER.info("保存参观指导管理图片-service");
        final ReturnInfo returnInfo = new ReturnInfo();
        try {
            final String fileUrl = saveFile(attachFile, constants.getRootPath(), CASE_INFO_GUIDANCE);
            returnInfo.setReason(fileUrl);
        } catch (IOException e) {
            LOGGER.error("文件上传异常:{}", e);
            returnInfo.setReason("文件上传异常!");
            returnInfo.setSuccess(FALSE);
        }
        return returnInfo;
    }
}
