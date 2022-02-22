package com.example.bishe.controller.caseinfo;

import com.example.bishe.config.Constants;
import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.controller.restaurant.RestaurantController;
import com.example.bishe.entity.caseinfo.CaseInfo;
import com.example.bishe.entity.caseinfo.CaseInfoEnclosure;
import com.example.bishe.entity.caseinfo.CaseQueryCondition;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.entity.restaurant.RestaurantQueryCondition;
import com.example.bishe.service.caseinfo.impl.CaseInfoServiceImpl;
import com.example.bishe.service.restaurant.impl.RestaurantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.beans.BeanUtils.copyProperties;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.bishe.config.Constants.*;
import static com.example.bishe.config.FileHelper.saveFile;

@RestController
@RequestMapping("/caseinfo")
public class CaseInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaseInfoController.class);

    private CaseInfoServiceImpl caseInfoServiceImpl;
    private final Constants constants;
    @Autowired
    public CaseInfoController(CaseInfoServiceImpl caseInfoServiceImpl,Constants constants) {
        this.caseInfoServiceImpl = caseInfoServiceImpl;
        this.constants = constants;
    }

    /**
     * 根据条件查询案件信息
     *
     * @param caseQueryCondition 案件查询条件实体类
     * @return JsonResponseVO
     */
    @PostMapping("/query")
    @ResponseBody
    public PagedQueryResult<CaseInfo> queryUserDetail(@RequestBody CaseQueryCondition caseQueryCondition) {
        LOGGER.info("开始根据条件查询案件信息-ServiceImpl");
        return caseInfoServiceImpl.queryCaseInfo(caseQueryCondition);
    }

    /**
     * 新增（修改）案件信息
     *
     * @param caseInfo 案件信息实体类
     * @param session       session
     * @return JsonResponseVO
     */


    @RequestMapping("/add")
    public JsonResponseVO addVisitGuidanceEnclosure(@RequestPart("caseInfo") CaseInfo caseInfo, @RequestPart("attachFile") MultipartFile[] attachFile,HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("案件信息-新增");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //新增或修改案件详细信息
        ReturnInfo returnInfo = caseInfoServiceImpl.addCaseInfo(caseInfo,attachFile);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 删除案件信息
     *
     * @param id 案件id
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/delete")
    public JsonResponseVO deleteCaseInfoDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("案件信息-删除-impl,id:{}",id);
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //删除案件信息
        ReturnInfo returnInfo = caseInfoServiceImpl.deleteCaseInfo(id);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 冻结案件信息
     *
     * @param id 案件id
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/freeze")
    public JsonResponseVO freezeCaseInfoDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-冻结案件信息-impl,id:{}",id);
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //冻结案件信息
        ReturnInfo returnInfo = caseInfoServiceImpl.freezeCaseInfo(id);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 附件上传
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestPart MultipartFile[] attachFile) {
        LOGGER.info("案件管理附件上传-controller");
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
                LOGGER.info("test",caseInfoEnclosure.getFileUrl());
            }
        } catch (IOException e) {
            LOGGER.error("上传文件失败:{}", e.getMessage());
            result.put(RESPONSE_SUCCESS_PARAM, false);
        }
        result.put(RESPONSE_RECORD_PARAM, reason);
        return result;
    }

    /**
     * 修改案件状态
     *
     * @param id 案件id status 案件状态(1-通过 2-不通过)
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/changeCaseInfoStatus")
    public JsonResponseVO changeCaseInfoStatus(Long id, Integer state) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-修改案件状态-impl,id:{},status:{}",id,state);
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //冻结案件信息
        ReturnInfo returnInfo = caseInfoServiceImpl.changeCaseInfoStatus(id,state);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

}
