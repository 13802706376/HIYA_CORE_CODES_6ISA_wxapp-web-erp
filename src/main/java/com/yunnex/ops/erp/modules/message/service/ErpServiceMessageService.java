package com.yunnex.ops.erp.modules.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.DateUtils;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.message.dao.ErpServiceMessageDao;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageAllRequestDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageAllResponseDto;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageResponseDto;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceMessage;
import com.yunnex.ops.erp.modules.sys.service.SysConstantsService;
import com.yunnex.ops.erp.modules.workflow.enums.FlowServiceType;

/**
 * 服务通知表Service
 * 
 * @author yunnex
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class ErpServiceMessageService extends CrudService<ErpServiceMessageDao, ErpServiceMessage> {

    @Autowired
    private SysConstantsService sysConstantsService;
    @Autowired
    private ServiceLinkVariableService serviceLinkVariableService;

    @Override
    public ErpServiceMessage get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpServiceMessage> findList(ErpServiceMessage erpServiceMessage) {
        return super.findList(erpServiceMessage);
    }

    @Override
    public Page<ErpServiceMessage> findPage(Page<ErpServiceMessage> page, ErpServiceMessage erpServiceMessage) {
        return super.findPage(page, erpServiceMessage);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpServiceMessage erpServiceMessage) {
        super.save(erpServiceMessage);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpServiceMessage erpServiceMessage) {
        super.delete(erpServiceMessage);
    }

    /**
     * 根据掌贝id和endTime的区间 获取服务通知列表
     *
     * @param zhangbeiId
     * @param startTime
     * @param endTime
     * @return
     * @date 2018年7月11日
     * @author linqunzhi
     */
    public List<ErpServiceMessage> findByZhangbeiIdEndTime(String zhangbeiId, Date startTime, Date endTime) {
        logger.info("findByZhangbeiIdEndTime start");
        String startTimeStr = DateUtils.formatDateTime(startTime);
        String endTimeStr = DateUtils.formatDateTime(endTime);
        logger.info("param | zhangbeiId={}|startTime={}|endTime={}", zhangbeiId, startTimeStr, endTimeStr);
        List<ErpServiceMessage> result = dao.findByZhangbeiIdEndTime(zhangbeiId, startTime, endTime);
        logger.info("findByZhangbeiIdEndTime end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 根据掌贝id 获取未结束服务通知集合
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     */
    public List<ErpServiceMessage> findNoEnd(String zhangbeiId) {
        logger.info("findByZhangbeiIdEndTime start | zhangbeiId={}", zhangbeiId);
        List<ErpServiceMessage> result = dao.findNoEnd(zhangbeiId);
        logger.info("findByZhangbeiIdEndTime end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 获取服务通知列表数据
     *
     * @param requestDto
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    public ServiceMessageAllResponseDto getServiceMessageAllResponseDto(ServiceMessageAllRequestDto requestDto) {
        logger.info("getServiceMessageAllResponseDto start");
        String requestDtoStr = JSON.toJSONString(requestDto);
        logger.info("param | requestDto={}", requestDtoStr);
        // 获取符合条件的服务通知数据
        List<ErpServiceMessage> messageList = dao.findListByDto(requestDto);
        // 根据服务通知列表 获取 服务通知response列表
        List<ServiceMessageResponseDto> list = getServiceMessageResponseDtoList(messageList);
        ServiceMessageAllResponseDto result = new ServiceMessageAllResponseDto();
        result.setCount(requestDto.getPage().getCount());
        result.setList(list);
        logger.info("getServiceMessageAllResponseDto end | list.size={}", list == null ? 0 : list.size());
        return result;
    }

    /**
     * 根据服务通知列表 获取 服务通知response列表
     *
     * @param messageList
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    private List<ServiceMessageResponseDto> getServiceMessageResponseDtoList(List<ErpServiceMessage> messageList) {
        if (CollectionUtils.isEmpty(messageList)) {
            return null;
        }
        Map<String, String> goodPhotoMap = sysConstantsService.getGoodPhotoMap();
        List<ServiceMessageResponseDto> result = new ArrayList<>();
        ServiceMessageResponseDto dto = null;
        for (ErpServiceMessage message : messageList) {
            String serviceType = message.getServiceType();
            dto = new ServiceMessageResponseDto();
            dto.setServiceName(FlowServiceType.getByType(serviceType).getName());
            dto.setServiceNums(message.getServiceNums());
            dto.setServiceLogo(goodPhotoMap.get(serviceType));
            dto.setContent(message.getContent());
            dto.setType(message.getType());
            dto.setLinkType(message.getLinkType());
            // 获取转换后的连接参数
            String linkParam = serviceLinkVariableService.replaceLinkParam(message.getProcInsId(), serviceType, message.getNodeType(),
                            message.getLinkParam());
            dto.setLinkParam(linkParam);
            dto.setStartDateStr(DateUtils.formatDate(message.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM));
            result.add(dto);
        }
        return result;
    }

    /**
     * 关闭消息
     *
     * @param id
     * @date 2018年7月23日
     * @author linqunzhi
     */
    @Transactional(readOnly = false)
    public void close(String id) {
        logger.info("close start | id={}", id);
        if (StringUtils.isBlank(id)) {
            logger.info("id 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ErpServiceMessage message = new ErpServiceMessage(id);
        message.setEndTime(new Date());
        this.save(message);
        logger.info("close end");
    }
}
