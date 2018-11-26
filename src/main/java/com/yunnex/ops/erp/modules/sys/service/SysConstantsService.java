package com.yunnex.ops.erp.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.order.constans.OrderGoodConstants;
import com.yunnex.ops.erp.modules.sys.dao.SysConstantsDao;
import com.yunnex.ops.erp.modules.sys.entity.SysConstants;

/**
 * 系统常量Service
 * 
 * @author linqunzhi
 * @version 2018-04-16
 */
@Service
@Transactional(readOnly = true)
public class SysConstantsService extends CrudService<SysConstantsDao, SysConstants> {

    public SysConstants get(String id) {
        return super.get(id);
    }

    public List<SysConstants> findList(SysConstants sysConstants) {
        return super.findList(sysConstants);
    }

    public Page<SysConstants> findPage(Page<SysConstants> page, SysConstants sysConstants) {
        return super.findPage(page, sysConstants);
    }

    @Transactional(readOnly = false)
    public void save(SysConstants sysConstants) {
        super.save(sysConstants);
    }

    @Transactional(readOnly = false)
    public void delete(SysConstants sysConstants) {
        super.delete(sysConstants);
    }

    /**
     * 根据key获取常量信息
     *
     * @param key
     * @return
     * @date 2018年4月16日
     * @author linqunzhi
     */
    public SysConstants getByKey(String key) {
        logger.info("getByKey start | key={}", key);
        if (StringUtils.isBlank(key)) {
            logger.error("key 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        SysConstants result = dao.getByKey(key);
        logger.info("getByKey end");
        return result;
    }

    /**
     * 获取商品分类图片路径
     *
     * @return
     * @date 2018年4月13日
     * @author linqunzhi
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getGoodPhotoMap() {
        // 客常来、聚引客 服务图片地址常量
        SysConstants dict = getByKey(OrderGoodConstants.GOOD_PHOTO_URLS_KEY);
        Map<String, String> result = new HashMap<>();
        if (dict != null) {
            String dictValue = dict.getValue();
            logger.info("服务图片地址：dictValue={}", dictValue);
            if (StringUtils.isNotBlank(dictValue)) {
                try {
                    result = JSON.parseObject(dictValue, Map.class);
                } catch (Exception e) {
                    logger.info("转换json出错！dictValue={}", dictValue, e);
                }
            }
        }
        return result;
    }

    /**
     * 根据多个key获取多个value值
     *
     * @param keyList
     * @return
     * @date 2018年7月16日
     * @author linqunzhi
     */
    public List<SysConstants> findByKeyList(List<String> keyList) {
        logger.info("findByKeyList start");
        String keyListStr = JSON.toJSONString(keyList);
        logger.info("param | keyList={}", keyListStr);
        List<SysConstants> result = dao.findByKeyList(keyList);
        logger.info("findByKeyList end | result.size={}", result == null ? 0 : result.size());
        return result;
    }

    public List<String> findValuesByKeyList(List<String> keyList) {
        logger.info("findValuesByKeyList start");
        String keyListStr = JSON.toJSONString(keyList);
        logger.info("param | keyList={}", keyListStr);
        List<SysConstants> list = dao.findByKeyList(keyList);
        List<String> result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<>();
            for (SysConstants constants : list) {
                String value = constants.getValue();
                if (value != null) {
                    result.add(value);
                }
            }
        }
        logger.info("findValuesByKeyList end | result.size={}", result == null ? 0 : result.size());
        return result;
    }
}
