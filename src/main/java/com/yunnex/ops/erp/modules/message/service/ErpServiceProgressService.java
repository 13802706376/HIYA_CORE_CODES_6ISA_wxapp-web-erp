package com.yunnex.ops.erp.modules.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.DateUtils;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.message.constant.ServiceLinkConstants;
import com.yunnex.ops.erp.modules.message.dao.ErpServiceProgressDao;
import com.yunnex.ops.erp.modules.message.dto.ServiceScheduleResponseDto;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceProgress;
import com.yunnex.ops.erp.modules.message.extraModel.ServiceProgressExtra;

/**
 * 服务进度表Service
 * @author yunnex
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class ErpServiceProgressService extends CrudService<ErpServiceProgressDao, ErpServiceProgress> {

    @Autowired
    private ServiceProgressVariableService scheduleVariableService;

    @Override
	public ErpServiceProgress get(String id) {
		return super.get(id);
	}

    @Override
	public List<ErpServiceProgress> findList(ErpServiceProgress erpServiceSchedule) {
		return super.findList(erpServiceSchedule);
	}

    @Override
	public Page<ErpServiceProgress> findPage(Page<ErpServiceProgress> page, ErpServiceProgress erpServiceSchedule) {
		return super.findPage(page, erpServiceSchedule);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(ErpServiceProgress erpServiceSchedule) {
		super.save(erpServiceSchedule);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(ErpServiceProgress erpServiceSchedule) {
		super.delete(erpServiceSchedule);
	}

    /**
     * 根据条件获取最大启动时间的服务进度
     *
     * @param zhangbeiId
     * @param userId
     * @param roleType
     * @return
     * @date 2018年7月6日
     * @author linqunzhi
     */
    public ServiceProgressExtra getBeginMaxStartTime(String zhangbeiId, String userId, String roleType) {
        logger.info("getMaxStartTime start | zhangbeiId={}|userId={}|roleType={}", zhangbeiId, userId, roleType);
        ServiceProgressExtra result = dao.getBeginMaxStartTime(zhangbeiId, userId, roleType);
        logger.info("getMaxStartTime end");
        return result;
    }

    /**
     * 流程id 是否有服务进度
     * 
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月10日
     * @author linqunzhi
     */
    public boolean hasByServiceType(String procInsId, String serviceType) {
        logger.info("hasByServiceType start | procInsId={}|serviceType={}", procInsId, serviceType);
        int count = dao.countByServiceType(procInsId, serviceType);
        boolean result = count > 0;
        logger.info("hasByServiceType end | result={}", result);
        return result;
    }

    /**
     * 根据流程id获取最大启动时间的服务进度
     *
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    public ServiceProgressExtra getBeginMaxStartTime(String procInsId, String serviceType) {
        logger.info("getMaxStartTime start | procInsId={}|serviceType={}", procInsId, serviceType);
        ServiceProgressExtra result = dao.getBeginMaxStartTimeByServiceType(procInsId, serviceType);
        logger.info("getMaxStartTime end");
        return result;
    }

    /**
     * 获取流程进度详情
     *
     * @param procInsId
     * @param serviceType
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    public List<ServiceScheduleResponseDto> getProcessInfo(String procInsId, String serviceType) {
        logger.info("getProcessInfo start | procInsId={}|serviceType={}", procInsId, serviceType);
        if (StringUtils.isBlank(procInsId) || StringUtils.isBlank(serviceType)) {
            logger.error("不能为空！procInsId={}|serviceType={}", procInsId, serviceType);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ServiceProgressExtra param = new ServiceProgressExtra();
        param.setProcInsId(procInsId);
        param.setServiceType(serviceType);
        List<ServiceProgressExtra> extraList = dao.findExtra(param);
        List<ServiceScheduleResponseDto> result = getServiceScheduleResponseDtoList(extraList);
        logger.info("getProcessInfo end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 获取流程进度详情 responseDto
     *
     * @param extraList
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    private List<ServiceScheduleResponseDto> getServiceScheduleResponseDtoList(List<ServiceProgressExtra> extraList) {
        if (CollectionUtils.isEmpty(extraList)) {
            return null;
        }
        List<ServiceScheduleResponseDto> result = new ArrayList<>();
        Map<String, Map<String, String>> validAllMap = new HashMap<>();
        ServiceScheduleResponseDto dto = null;
        for (ServiceProgressExtra extra : extraList) {
            // 服务类型
            String serviceType = extra.getServiceType();
            // 流程id
            String procInsId = extra.getProcInsId();
            String key = procInsId + serviceType;
            // 所有变量map集合
            Map<String, String> variableMap = validAllMap.get(key);
            if (variableMap == null) {
                //流程id和服务类型
                variableMap = scheduleVariableService.getVariableMap(extra.getZhangbeiId(), procInsId, serviceType);
                validAllMap.put(key, variableMap);
            }
            String content = extra.getContent() == null ? "" : extra.getContent();
            String linkParam = extra.getLinkParam() == null ? "" : extra.getLinkParam();

            if (variableMap != null) {
                // 将变量变成实际值
                for (Entry<String, String> entry : variableMap.entrySet()) {
                    String entryKey = entry.getKey();
                    String entryValue = entry.getValue();
                    content = content.replace(entryKey, entryValue);
                    linkParam = linkParam.replace(entryKey, entryValue);
                }
            }
            // 获取单个进度的变量值
            Map<String, String> nodeTypeMap = scheduleVariableService.getNodeTypeMap(procInsId, serviceType, extra.getType());
            if (nodeTypeMap != null) {
                // 将变量变成实际值
                for (Entry<String, String> entry : nodeTypeMap.entrySet()) {
                    String entryKey = entry.getKey();
                    String entryValue = entry.getValue();
                    content = content.replace(entryKey, entryValue);
                    linkParam = linkParam.replace(entryKey, entryValue);
                }
            }
            // 组装dto对象
            dto = new ServiceScheduleResponseDto();
            dto.setName(extra.getName());
            dto.setType(extra.getType());
            dto.setStartTimeStr(DateUtils.formatDate(extra.getStartTime(), DateUtils.YYYY_MM_DD));
            dto.setContent(content);
            // 是否展示连接
            boolean notShowLink = notShowLink(extra.getLinkType(), linkParam);
            dto.setLinkType(notShowLink ? null : extra.getLinkType());
            dto.setLinkParam(linkParam);
            dto.setType(extra.getType());
            dto.setStatus(extra.getStatus());
            // 将dto加入返回集合中
            result.add(dto);
        }
        return result;
    }

    /**
     * 是否展示交互入口
     *
     * @param linkType
     * @param linkParam
     * @return
     * @date 2018年7月30日
     * @author linqunzhi
     */
    private boolean notShowLink(String linkType, String linkParam) {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(linkParam)) {
            json = JSON.parseObject(linkParam);
        }
        // 服务验收及评价页
        if (ServiceLinkConstants.ServiceAcceptance.type.equals(linkType)) {
            String visitFlag = json.getString(ServiceLinkConstants.ServiceAcceptance.Param.VISIT_FLAG);
            if (CommonConstants.Sign.NO.equals(visitFlag)) {
                return true;
            }
        }
        return false;
    }
}