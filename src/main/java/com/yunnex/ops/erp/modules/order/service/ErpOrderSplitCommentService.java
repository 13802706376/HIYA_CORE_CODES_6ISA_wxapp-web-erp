package com.yunnex.ops.erp.modules.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunnex.ops.erp.common.constants.CommonConstants;
import com.yunnex.ops.erp.common.persistence.Page;
import com.yunnex.ops.erp.common.service.CrudService;
import com.yunnex.ops.erp.common.service.ServiceException;
import com.yunnex.ops.erp.modules.order.constans.SplitCommentConstants;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderSplitCommentDao;
import com.yunnex.ops.erp.modules.order.dao.ErpOrderSplitInfoDao;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitComment;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentAConf;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentAnswer;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentQConf;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentQuestion;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitInfo;
import com.yunnex.ops.erp.modules.order.param.SplitCommentAnswerParam;
import com.yunnex.ops.erp.modules.order.param.SplitCommentParam;
import com.yunnex.ops.erp.modules.order.view.SplitCommentAnswerConfView;
import com.yunnex.ops.erp.modules.order.view.SplitCommentQuetionConfView;
import com.yunnex.ops.erp.modules.shop.entity.ErpShopInfo;
import com.yunnex.ops.erp.modules.store.common.ShopUtils;

import net.sf.json.JSONObject;

/**
 * 聚引客分单评论Service
 * 
 * @author yunnex
 * @version 2018-01-30
 */
@Service
@Transactional(readOnly = true)
public class ErpOrderSplitCommentService extends CrudService<ErpOrderSplitCommentDao, ErpOrderSplitComment> {

    @Autowired
    private ShopUtils shopUtils;
    @Autowired
    private ErpOrderSplitInfoDao erpOrderSplitInfoDao;
    @Autowired
    private ErpOrderSplitCommentQConfService erpOrderSplitCommentQConfService;
    @Autowired
    private ErpOrderSplitCommentAConfService erpOrderSplitCommentAConfService;
    @Autowired
    private ErpOrderSplitCommentQuestionService erpOrderSplitCommentQustionService;
    @Autowired
    private ErpOrderSplitCommentAnswerService erpOrderSplitCommentAnswerService;

    @Override
    public ErpOrderSplitComment get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpOrderSplitComment> findList(ErpOrderSplitComment erpOrderSplitComment) {
        return super.findList(erpOrderSplitComment);
    }

    @Override
    public Page<ErpOrderSplitComment> findPage(Page<ErpOrderSplitComment> page, ErpOrderSplitComment erpOrderSplitComment) {
        return super.findPage(page, erpOrderSplitComment);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpOrderSplitComment erpOrderSplitComment) {
        super.save(erpOrderSplitComment);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpOrderSplitComment erpOrderSplitComment) {
        super.delete(erpOrderSplitComment);
    }


    /**
     * 提交分单评价
     *
     * @param comment
     * @date 2018年4月4日
     * @author linqunzhi
     */
    @Transactional(readOnly = false)
    public void comment(SplitCommentParam splitCommentParam) {
        logger.info("comment start | {}", JSONObject.fromObject(splitCommentParam));
        if (splitCommentParam == null) {
            logger.info("splitCommentParam 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // 分单id
        String splitId = splitCommentParam.getSplitId();
        if (StringUtils.isBlank(splitId)) {
            logger.info("splitId 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // 分单信息
        ErpOrderSplitInfo splitInfo = erpOrderSplitInfoDao.get(splitId);
        if (splitInfo == null) {
            logger.info("分单不存在！splitId={}", splitId);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // 新增评价
        ErpOrderSplitComment comment = new ErpOrderSplitComment();
        ErpShopInfo loginShop = shopUtils.getLoginShop();
        String shopId = loginShop.getId();
        comment.setShopId(shopId);
        comment.setSplitId(splitId);
        comment.setServiceScore(splitCommentParam.getServiceScore());
        comment.setPromotionScore(splitCommentParam.getPromotionScore());
        comment.setSplitId(splitInfo.getId());
        save(comment);
        // 修改分单评价总数
        int commentCount = splitInfo.getCommentCount() + 1;
        erpOrderSplitInfoDao.updateCommentCount(splitInfo.getId(), commentCount);
        // 新增问答数据
        addQuestionAnswerList(comment.getId(), splitCommentParam.getAnswerList());

    }

    /**
     * 新增问答数据
     *
     * @param splitId
     * @param answerList
     * @date 2018年4月4日
     * @author linqunzhi
     */
    private void addQuestionAnswerList(String commentId, List<SplitCommentAnswerParam> answerList) {
        // 获取所有问题配置信息
        List<ErpOrderSplitCommentQConf> qConfList = erpOrderSplitCommentQConfService.findList(new ErpOrderSplitCommentQConf());
        if (qConfList != null) {
            for (ErpOrderSplitCommentQConf qConf : qConfList) {
                // 新增问题
                ErpOrderSplitCommentQuestion question = new ErpOrderSplitCommentQuestion();
                question.setCommentId(commentId);
                question.setContent(qConf.getContent());
                question.setRank(qConf.getRank());
                question.setType(qConf.getType());
                erpOrderSplitCommentQustionService.save(question);
                // 回答配置信息表
                List<ErpOrderSplitCommentAConf> aConfList = erpOrderSplitCommentAConfService.findListByQuestionId(qConf.getId());
                if (aConfList != null) {
                    for (ErpOrderSplitCommentAConf aConf : aConfList) {
                        ErpOrderSplitCommentAnswer answer = new ErpOrderSplitCommentAnswer();
                        answer.setCheckFlag(SplitCommentConstants.Answer.CHECK_NO);
                        answer.setQuestionId(question.getId());
                        answer.setRank(aConf.getRank());
                        // 如果问题类型是非文本型，则将内容赋值
                        if (!SplitCommentConstants.Question.TYPE_TEXT.equals(qConf.getType())) {
                            // 接收回答配置表里的回答内容
                            answer.setContent(aConf.getContent());
                        }
                        if (answerList != null) {
                            int answerListSize = answerList.size();
                            for (int i = 0; i < answerListSize; i++) {
                                SplitCommentAnswerParam answerParam = answerList.get(i);
                                String answerParamId = answerParam.getId();
                                if (StringUtils.isBlank(answerParamId)) {
                                    logger.info("回答配置id为空");
                                    throw new ServiceException(CommonConstants.FailMsg.PARAM);
                                }
                                String aConfId = aConf.getId();
                                // 如果回答配置id 与 回答参数中的回答id一样
                                if (answerParamId.equals(aConfId)) {
                                    // 如果问题是文本类型
                                    if (SplitCommentConstants.Question.TYPE_TEXT.equals(qConf.getType())) {
                                        // 接收参数里的回答内容
                                        answer.setContent(answerParam.getContent());
                                        answer.setCheckFlag(SplitCommentConstants.Answer.CHECK_NO);
                                    } else {
                                        answer.setCheckFlag(SplitCommentConstants.Answer.CHECK_YES);
                                    }
                                }
                            }
                            erpOrderSplitCommentAnswerService.save(answer);
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取评价配置信息列表视图
     *
     * @return
     * @date 2018年4月3日
     * @author linqunzhi
     */
    public List<SplitCommentQuetionConfView> findCommentConfViewList() {
        logger.info("findCommentConfViewList start");
        List<SplitCommentQuetionConfView> result = null;
        // 获取所有问题配置信息
        List<ErpOrderSplitCommentQConf> qList = erpOrderSplitCommentQConfService.findList(new ErpOrderSplitCommentQConf());
        if (qList != null) {
            result = new ArrayList<>();
            // 分单评价问题配置视图
            SplitCommentQuetionConfView view = null;
            for (ErpOrderSplitCommentQConf q : qList) {
                String questionId = q.getId();
                // 根据问题配置id获取回答配置信息集合
                List<ErpOrderSplitCommentAConf> aList = erpOrderSplitCommentAConfService.findListByQuestionId(questionId);
                view = convertQuetionConfView(q, aList);
                result.add(view);
            }
        }
        logger.info("findCommentConfViewList end | resutl.size={}", result == null ? 0 : result.size());
        return result;
    }

    /**
     * 转换问题配置视图
     *
     * @param erpOrderSplitCommentQConf
     * @param erpOrderSplitCommentAConfList
     * @return
     * @date 2018年4月4日
     * @author linqunzhi
     */
    private SplitCommentQuetionConfView convertQuetionConfView(ErpOrderSplitCommentQConf erpOrderSplitCommentQConf,
                    List<ErpOrderSplitCommentAConf> erpOrderSplitCommentAConfList) {
        SplitCommentQuetionConfView view = null;
        if (erpOrderSplitCommentQConf != null) {
            // 回答视图集合
            List<SplitCommentAnswerConfView> answerViewList = null;
            // 回答问题视图
            SplitCommentAnswerConfView answerView = null;
            if (erpOrderSplitCommentAConfList != null) {
                answerViewList = new ArrayList<>();
                // 转换回答视图
                for (ErpOrderSplitCommentAConf ans : erpOrderSplitCommentAConfList) {
                    answerView = new SplitCommentAnswerConfView();
                    answerView.setAnswerId(ans.getId());
                    answerView.setAnswerContent(ans.getContent());
                    answerView.setQuestionId(ans.getQuestionId());
                    answerViewList.add(answerView);
                }
            }
            // 给问题视图赋值
            view = new SplitCommentQuetionConfView();
            view.setQuestionId(erpOrderSplitCommentQConf.getId());
            view.setQuetionContent(erpOrderSplitCommentQConf.getContent());
            view.setQuetionType(erpOrderSplitCommentQConf.getType());
            view.setQuestionMustFlag(erpOrderSplitCommentQConf.getMustFlag());
            view.setAnswerList(answerViewList);
        }
        return view;
    }

}
