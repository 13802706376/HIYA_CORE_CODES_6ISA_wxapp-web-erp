package com.yunnex.ops.erp.modules.workflow.channel.dao;

import java.util.List;

import com.yunnex.ops.erp.common.persistence.CrudDao;
import com.yunnex.ops.erp.common.persistence.annotation.MyBatisDao;
import com.yunnex.ops.erp.modules.workflow.channel.entity.JykOrderPromotionChannel;

/**
 * 聚引客订单推广渠道管理DAO接口
 * @author Frank
 * @version 2017-10-27
 */
@MyBatisDao
public interface JykOrderPromotionChannelDao extends CrudDao<JykOrderPromotionChannel> {
	
   List<JykOrderPromotionChannel> findListBySplitId(String splitId);
}