package com.yunnex.ops.erp.modules.message.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.message.dto.ServiceMessageAllRequestDto;
import com.yunnex.ops.erp.modules.message.entity.ErpServiceMessage;

/**
 * 服务通知表DAO接口
 * @author yunnex
 * @version 2018-07-04
 */
@MyBatisDao
public interface ErpServiceMessageDao extends CrudDao<ErpServiceMessage> {

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
    List<ErpServiceMessage> findByZhangbeiIdEndTime(@Param("zhangbeiId") String zhangbeiId, @Param("startTime") Date startTime,
                    @Param("endTime") Date endTime);

    /**
     * 根据掌贝id 获取未结束服务通知集合
     *
     * @param zhangbeiId
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     */
    List<ErpServiceMessage> findNoEnd(@Param("zhangbeiId") String zhangbeiId);

    /**
     * 根据条件获取 服务通知列表数据
     *
     * @param requestDto
     * @return
     * @date 2018年7月12日
     * @author linqunzhi
     */
    List<ErpServiceMessage> findListByDto(ServiceMessageAllRequestDto requestDto);
	
}