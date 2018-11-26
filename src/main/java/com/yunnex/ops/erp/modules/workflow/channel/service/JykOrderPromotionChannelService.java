package com.yunnex.ops.erp.modules.workflow.channel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.modules.workflow.channel.dao.JykOrderPromotionChannelDao;
import com.yunnex.ops.erp.modules.workflow.channel.entity.JykOrderPromotionChannel;

/**
 * 聚引客订单推广渠道管理Service
 * @author Frank
 * @version 2017-10-27
 */
@Service
@Transactional(readOnly = true)
public class JykOrderPromotionChannelService extends CrudService<JykOrderPromotionChannelDao, JykOrderPromotionChannel> {

    @Override
	public JykOrderPromotionChannel get(String id) {
		return super.get(id);
	}
	public List<JykOrderPromotionChannel> findListBySplitId(String splitId) {
        return dao.findListBySplitId(splitId);
    }

    @Override
	public List<JykOrderPromotionChannel> findList(JykOrderPromotionChannel jykOrderPromotionChannel) {
		return super.findList(jykOrderPromotionChannel);
	}

    @Override
	public Page<JykOrderPromotionChannel> findPage(Page<JykOrderPromotionChannel> page, JykOrderPromotionChannel jykOrderPromotionChannel) {
		return super.findPage(page, jykOrderPromotionChannel);
	}

    @Override
	@Transactional(readOnly = false)
	public void save(JykOrderPromotionChannel jykOrderPromotionChannel) {
		super.save(jykOrderPromotionChannel);
	}

    @Override
	@Transactional(readOnly = false)
	public void delete(JykOrderPromotionChannel jykOrderPromotionChannel) {
		super.delete(jykOrderPromotionChannel);
	}
	
	
	
}