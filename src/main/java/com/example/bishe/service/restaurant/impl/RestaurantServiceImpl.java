package com.example.bishe.service.restaurant.impl;

import com.example.bishe.config.*;
import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;

import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.entity.restaurant.RestaurantQueryCondition;
import com.example.bishe.mapper.personalmanagement.PersonalManagementMapper;
import com.example.bishe.mapper.restaurant.RestaurantMapper;
import com.example.bishe.service.personalmanagement.impl.PersonalManagementServiceImpl;
import com.example.bishe.service.restaurant.RestaurantService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.bishe.config.Constants.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private RestaurantMapper restaurantMapper;

    private final ExportTemplateUtils exportTemplateUtils = new ExportTemplateUtils();

    @Autowired
    public RestaurantServiceImpl(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }


    /**
     * 餐馆信息-查询餐馆信息
     *
     * @param restaurantQueryCondition 餐馆信息查询实体类
     * @return 餐馆信息实体类列表
     */
    @Override
    public PagedQueryResult<RestaurantInfo> queryRestaurant(RestaurantQueryCondition restaurantQueryCondition) {
        LOGGER.info("开始根据条件查询餐馆信息-RestaurantServiceImpl");

        final PagedQueryResult<RestaurantInfo> pagedQueryResult = new PagedQueryResult<>();

        //查询符合条件的数量
         final Integer count = restaurantMapper.queryRestaurantCount(restaurantQueryCondition);

        //有记录则查询
        if (count != null && !NUM_ZERO.equals(count)) {
            pagedQueryResult.setTotalCount(count);
            pagedQueryResult.setResults(restaurantMapper.queryRestaurant(restaurantQueryCondition));
        } else {
            pagedQueryResult.setTotalCount(NUM_ZERO);
            pagedQueryResult.setResults(Lists.newArrayList());
        }
        return pagedQueryResult;
    }

    /**
     * 餐馆信息-新增（修改）餐馆信息
     *
     * @param restaurantInfo 餐馆信息实体类
     * @return 是否成功新增或修改
     */
    @Override
    public ReturnInfo addRestaurant(RestaurantInfo restaurantInfo) {
        final ReturnInfo returnInfo = new ReturnInfo();
        if (NUM_ONE.equals(restaurantMapper.judgeRestaurantExistence(restaurantInfo.getId()))){
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("餐馆信息-修改餐馆信息-impl,id:{}");
            }
            //修改案件详细信息
            returnInfo.setSuccess(restaurantMapper.updateRestaurant(restaurantInfo,restaurantInfo.getId()));
        }else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("餐馆信息-添加餐馆信息-impl,id:{}");
            }
            //添加案件详细信息
            returnInfo.setSuccess(restaurantMapper.addRestaurant(restaurantInfo));
        }
        return returnInfo;
    }

    /**
     * 餐馆信息-删除餐馆信息
     *
     * @param id 餐馆id
     * @return 是否成功删除
     */
    @Override
    public ReturnInfo deleteRestaurant(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("餐馆信息-删除餐馆信息-impl,id:{}",id);
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        //删除案件信息
        returnInfo.setSuccess(restaurantMapper.deleteRestaurant(id));
        return returnInfo;
    }

    /**
     * 餐馆信息-冻结餐馆信息
     *
     * @param id 餐馆id
     * @return 是否成功冻结
     */
    @Override
    public ReturnInfo freezeRestaurant(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("餐馆信息-删除餐馆信息-impl,id:{}",id);
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        //冻结案件信息
        returnInfo.setSuccess(restaurantMapper.freezeRestaurant(id));
        return returnInfo;
    }

    @Override
    public Boolean modifyRestaurantState(Long id, Integer state) {
        return null;
    }

    /**
     * 下载导入模板
     *
     * @param response the response
     */
    @Override
    public void exportTemplate(HttpServletResponse response) {
        exportTemplateUtils.getTemplate(response, RESTAURANT_TEMPLATE_URL);
    }

    /**
     * 餐馆信息-导入月报
     *
     * @param file     excel表格文件
     * @param userName 操作人姓名
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResponseVO importRestaurant(MultipartFile file, String userName) {
        final JsonResponseVO jsonResponseVO = new JsonResponseVO();
        InputStream in = null;
        if (file.isEmpty()) {
            jsonResponseVO.setSuccess(Boolean.FALSE);
            jsonResponseVO.setReason("表格为空，请重新上传excel");
            return jsonResponseVO;
        }
        //导入月报
        List<List<Object>> excelList;
        Date dateTemp = null;
        Integer year1 = 0;
        Integer month1 = 0;

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int i = 1;
        List<String> checkList = new ArrayList();
        try {
            in = file.getInputStream();
            String[] title = new String[]{"餐馆名称", "餐馆负责人", "餐馆负责人电话", "餐馆地址", "本年度检查次数", "本年度检查通过次数","需关注程度(1-正常, 2-稍差,3-严重)","最近检查时间"};

            excelList = new ExcelUtils().getListByExcelToBeNullCheckModel(in, file.getOriginalFilename(), NUM_ONE, title);
            final ArrayList<RestaurantInfo> resultList = new ArrayList<>();

            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (List<Object> item : excelList) {
                i++;
                // 如果遍历到空行，则跳过
                if (org.apache.commons.lang.StringUtils.isEmpty(item.get(0).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(1).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(2).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(3).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(4).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(5).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(6).toString())
                        && org.apache.commons.lang.StringUtils.isEmpty(item.get(7).toString())
                ) {
                    continue;
                }


                final RestaurantInfo entity;
                entity = this.calibrateReportDetailEntity(item);



                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                LOGGER.info("f:{}",item.get(7).toString());
                dateTemp = sdf1.parse(item.get(7).toString());
                if (dateTemp != null) {
                    calendar1.setTime(dateTemp);
                }

                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM");


                calendar2.setTime(dateTemp);

                //如果遇到日期不合法或者底下有不合法输入，报错
                try {
                    Date date2 = null;
                    format.setLenient(false);
                    date2=format.parse(item.get(7).toString());
                    if(!item.get(7).toString().equals(format.format(date2))){
                        throw new ImportArrayException("报表中的第" + i + "行日期格式错误，请确认！");
                    }
                } catch (ParseException  | ImportArrayException e) {
                    throw new ImportArrayException("报表中的第" + i + "行日期格式错误，请确认！");
                } catch (Exception e) {
                    throw new ImportArrayException("导入的excel表格与模板不一致，请重新确认！");
                }

                if (!isNumeric(entity.getYearCheckCount())) {
                    throw new ImportException("第" + i + "行小计数量必须是十位以内的正整数");
                }
                if (!isNumeric(entity.getYearPassCount())) {
                    throw new ImportException("第" + i + "行小计数量必须是十位以内的正整数");
                }
                if (!isPhone(entity.getLegalPersonPhone())) {
                    throw new ImportException("第" + i + "行手机号码格式不正确");
                }



                //判断未来日期
                Date futureCompare = format.parse(item.get(7).toString());
                if(futureCompare.compareTo(new Date()) == NUM_ONE) {
                    throw new ImportArrayException("未来的时间不能导入！");
                }


                final RestaurantInfo realEntity = calibrateReportDetailEntity(item);

                resultList.add(realEntity);
            }

            if (resultList.size() == 0) {
                throw new ImportException("报表中无数据，请确认！");
            }

            for (RestaurantInfo realEntity : resultList) {

                restaurantMapper.addRestaurant(realEntity);
            }
            jsonResponseVO.setReason("导入成功!");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ImportArrayException(e);
        }
        return jsonResponseVO;
    }




    /**
     * 餐馆信息-校验月报详情数据
     */
    private RestaurantInfo calibrateReportDetailEntity(List<Object> item) {
        final RestaurantInfo entity = new RestaurantInfo();
        entity.setRestaurantName(item.get(0).toString());
        entity.setLegalPersonName(item.get(1).toString());
        entity.setLegalPersonPhone(item.get(2).toString());
        entity.setAddress(item.get(3).toString());
        entity.setYearCheckCount(item.get(4).toString());
        entity.setYearPassCount(item.get(5).toString());
        entity.setAttention(item.get(6).toString());
        entity.setRecentCheckTime(item.get(7).toString());
        return entity;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(0|[^0-][0-9]{0,9})(\\.[0]?)*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    /**
     * 利用正则表达式判断字符串是否是手机号码
     *
     * @param str
     * @return
     */
    public boolean isPhone(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * restaurantInfo 餐馆信息
     */
    @Override
    public RestaurantInfo extractRestaurant() {
        return restaurantMapper.extractRestaurant();
    }

}
