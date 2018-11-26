package com.yunnex.ops.erp.modules.workflow.acceptance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yunnex.ops.erp.common.utils.HttpUtil;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.message.constant.ServiceMessageUrlConstants;
import com.yunnex.ops.erp.modules.order.constans.SplitCommentQuestionConstants;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentAnswer;
import com.yunnex.ops.erp.modules.order.entity.ErpOrderSplitCommentQuestion;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitCommentAnswerService;
import com.yunnex.ops.erp.modules.order.service.ErpOrderSplitCommentQuestionService;
import com.yunnex.ops.erp.modules.visit.constant.VisitServiceItemConstants;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceInfo;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceItem;
import com.yunnex.ops.erp.modules.visit.entity.ErpVisitServiceProductRecord;
import com.yunnex.ops.erp.modules.visit.service.ErpVisitServiceInfoService;
import com.yunnex.ops.erp.modules.visit.service.ErpVisitServiceItemService;
import com.yunnex.ops.erp.modules.visit.service.ErpVisitServiceProductRecordService;
import com.yunnex.ops.erp.modules.workflow.acceptance.dao.ErpServiceAcceptanceDao;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.AcceptanceAnswerRequestDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.AcceptanceAnswerResponseDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.AcceptanceQuestionRequestDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.AcceptanceQuestionResponseDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.ServiceAcceptanceRequestDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.dto.ServiceAcceptanceResponseDto;
import com.yunnex.ops.erp.modules.workflow.acceptance.entity.ErpServiceAcceptance;

/**
 * 服务验收评价Service
 * 
 * @author yunnex
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly = true)
public class ErpServiceAcceptanceService extends CrudService<ErpServiceAcceptanceDao, ErpServiceAcceptance> {

    @Autowired
    private ErpOrderSplitCommentQuestionService splitQuestionService;
    @Autowired
    private ErpOrderSplitCommentAnswerService splitAnswerService;
    @Autowired
    private ErpVisitServiceInfoService visitServiceInfoService;
    @Autowired
    private ErpVisitServiceItemService visitServiceItemService;
    @Autowired
    private ErpVisitServiceProductRecordService visitProductRecordService;

    @Override
    public ErpServiceAcceptance get(String id) {
        return super.get(id);
    }

    @Override
    public List<ErpServiceAcceptance> findList(ErpServiceAcceptance erpServiceAcceptance) {
        return super.findList(erpServiceAcceptance);
    }

    @Override
    public Page<ErpServiceAcceptance> findPage(Page<ErpServiceAcceptance> page, ErpServiceAcceptance erpServiceAcceptance) {
        return super.findPage(page, erpServiceAcceptance);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(ErpServiceAcceptance erpServiceAcceptance) {
        super.save(erpServiceAcceptance);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ErpServiceAcceptance erpServiceAcceptance) {
        super.delete(erpServiceAcceptance);
    }

    /**
     * 获取服务验收及评分页面数据
     *
     * @param id
     * @param visitServiceInfoId
     * @return
     * @date 2018年7月13日
     * @author linqunzhi
     */
    public ServiceAcceptanceResponseDto getServiceAcceptanceResponseDto(String visitServiceInfoId) {
        logger.info("getServiceAcceptanceResponseDto start |visitServiceInfoId={}", visitServiceInfoId);
        ServiceAcceptanceResponseDto result = null;
        ErpServiceAcceptance acceptance = this.getByVisitId(visitServiceInfoId);
        String id = acceptance == null ? null : acceptance.getId();
        if (StringUtils.isNotBlank(id)) {
            // 获取已提交数据
            result = getAcceptanceResponseDto(id);
        } else {
            // 获取未提交的页面数据
            result = getNoCommitAcceptanceResponseDto(visitServiceInfoId);
        }
        logger.info("getServiceAcceptanceResponseDto end");
        return result;
    }

    private ServiceAcceptanceResponseDto getNoCommitAcceptanceResponseDto(String visitServiceInfoId) {
        // 交付产品处理
        ErpVisitServiceProductRecord productRecord = new ErpVisitServiceProductRecord();
        productRecord.setVisitServiceInfoId(visitServiceInfoId);
        List<ErpVisitServiceProductRecord> productRecordList = visitProductRecordService.findList(productRecord);
        List<AcceptanceQuestionResponseDto> questionList = new ArrayList<>();
        int questionRank = 0;
        if (CollectionUtils.isNotEmpty(productRecordList)) {
            AcceptanceQuestionResponseDto question = new AcceptanceQuestionResponseDto();
            question.setQuetionContent("请确认完成交付的产品");
            List<AcceptanceAnswerResponseDto> answerList = new ArrayList<>();
            question.setAnswerList(answerList);
            question.setQuestionMustFlag(CommonConstants.Sign.NO);
            question.setQuetionType(SplitCommentQuestionConstants.TYPE_SELECT_MULTIPLE_TABLE);
            questionRank = questionRank + 1;
            question.setRank(questionRank);
            questionList.add(question);
            List<String> titleList = new ArrayList<>();
            titleList.add("产品");
            titleList.add("型号");
            titleList.add("数量");
            AcceptanceAnswerResponseDto answer = new AcceptanceAnswerResponseDto();
            int answerRank = 0;
            answer.setAnswerContent(JSON.toJSONString(titleList));
            answer.setCheckFlag(CommonConstants.Sign.NO);
            answerRank = answerRank + 1;
            answer.setRank(answerRank);
            answerList.add(answer);
            for (ErpVisitServiceProductRecord obj : productRecordList) {
                answer = new AcceptanceAnswerResponseDto();
                List<String> list = new ArrayList<>();
                list.add(obj.getProdName());
                list.add(obj.getProdType());
                list.add(obj.getProdNum());
                answer.setAnswerContent(JSON.toJSONString(list));
                answer.setCheckFlag(CommonConstants.Sign.NO);
                answerRank = answerRank + 1;
                answer.setRank(answerRank);

                answerList.add(answer);
            }
        }
        // 服务项目处理
        List<ErpVisitServiceItem> visitItemList = visitServiceItemService.findByVisitInfoId(visitServiceInfoId);
        if (CollectionUtils.isNotEmpty(visitItemList)) {
            AcceptanceQuestionResponseDto question = new AcceptanceQuestionResponseDto();
            question.setQuetionContent("请确认完成交付的服务项目");
            List<AcceptanceAnswerResponseDto> answerList = new ArrayList<>();
            question.setAnswerList(answerList);
            question.setQuestionMustFlag(CommonConstants.Sign.NO);
            question.setQuetionType(SplitCommentQuestionConstants.TYPE_SELECT_MULTIPLE);
            questionRank = questionRank + 1;
            question.setRank(questionRank);
            questionList.add(question);
            int answerRank = 0;
            AcceptanceAnswerResponseDto answer = null;
            for (ErpVisitServiceItem item : visitItemList) {
                // 现场休息 排除
                if (VisitServiceItemConstants.SERVICE_FLAG_6.equals(item.getServiceFlag())) {
                    continue;
                }
                answerRank = answerRank + 1;
                answer = new AcceptanceAnswerResponseDto();
                answer.setAnswerContent(item.getServiceItem());
                answer.setCheckFlag(CommonConstants.Sign.NO);
                answer.setRank(answerRank);
                answerList.add(answer);
            }
        }
        String serviceGoal = visitServiceItemService.getServiceGoalName(visitServiceInfoId);
        ServiceAcceptanceResponseDto result = new ServiceAcceptanceResponseDto();
        result.setVisitServiceInfoId(visitServiceInfoId);
        result.setQuestionList(questionList);
        result.setScore(0.0);
        result.setServiceGoal(serviceGoal);
        return result;
    }

    /**
     * 根据id获取服务验收及评分页面数据
     *
     * @param id
     * @date 2018年7月13日
     * @author linqunzhi
     */
    public ServiceAcceptanceResponseDto getAcceptanceResponseDto(String id) {
        logger.info("getAcceptanceResponseDto start | id={}", id);
        if (StringUtils.isBlank(id)) {
            logger.info("id 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // #### 服务验收及评分表数据处理 start ########
        // 获取服务验收及评分表数据
        ErpServiceAcceptance acceptance = this.get(id);
        if (acceptance == null) {
            logger.info("服务验收及评分数据不存在 ！id={}", id);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ServiceAcceptanceResponseDto result = new ServiceAcceptanceResponseDto();
        result.setVisitServiceInfoId(acceptance.getVisitInfoId());
        result.setScore(acceptance.getScore());
        result.setServiceGoal(acceptance.getServiceGoal());
        result.setId(id);
        // #### 服务验收及评分表数据处理 end ########

        // #### 问题列表数据处理 start ########
        ErpOrderSplitCommentQuestion question = new ErpOrderSplitCommentQuestion();
        question.setCommentId(id);
        // 获取问题列表
        List<ErpOrderSplitCommentQuestion> questionList = splitQuestionService.findList(question);
        if (CollectionUtils.isNotEmpty(questionList)) {
            List<AcceptanceQuestionResponseDto> questionDtoList = new ArrayList<>();
            result.setQuestionList(questionDtoList);
            AcceptanceQuestionResponseDto questionDto = null;
            for (ErpOrderSplitCommentQuestion ques : questionList) {
                questionDto = new AcceptanceQuestionResponseDto();
                questionDto.setQuetionContent(ques.getContent());
                questionDto.setQuetionType(ques.getType());
                questionDto.setQuestionMustFlag(CommonConstants.Sign.YES);
                questionDto.setRank(ques.getRank());

                questionDtoList.add(questionDto);
                // #### 回答列表数据处理 start ########
                ErpOrderSplitCommentAnswer answer = new ErpOrderSplitCommentAnswer();
                answer.setQuestionId(ques.getId());
                List<ErpOrderSplitCommentAnswer> answerList = splitAnswerService.findList(answer);
                if (CollectionUtils.isNotEmpty(answerList)) {
                    List<AcceptanceAnswerResponseDto> answerDtoList = new ArrayList<>();
                    questionDto.setAnswerList(answerDtoList);
                    AcceptanceAnswerResponseDto answerDto = null;
                    for (ErpOrderSplitCommentAnswer ans : answerList) {
                        answerDto = new AcceptanceAnswerResponseDto();
                        answerDto.setAnswerContent(ans.getContent());
                        answerDto.setCheckFlag(ans.getCheckFlag());
                        answerDto.setRank(ans.getRank());

                        answerDtoList.add(answerDto);
                    }
                }
                // #### 回答列表数据处理 end ########
            }
        }
        // #### 问题列表数据处理 end ########
        logger.info("getAcceptanceResponseDto end");
        return result;
    }

    /**
     * 提交服务验收及评分
     *
     * @param requestDto
     * @return
     * @date 2018年7月13日
     * @author linqunzhi
     */
    @Transactional(readOnly = false)
    public String commit(ServiceAcceptanceRequestDto requestDto) {
        logger.info("commit start");
        String requestDtoStr = JSON.toJSONString(requestDto);
        logger.info("param  | requestDto={}", requestDtoStr);
        if (requestDto == null) {
            logger.info("requestDto 不能为空");
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        // #### 服务验收及评分表数据处理 start ########
        // 获取服务验收及评分表数据
        String visitServiceInfoId = requestDto.getVisitServiceInfoId();
        ErpServiceAcceptance existAcceptance = this.getByVisitId(visitServiceInfoId);
        if (existAcceptance != null) {
            logger.info("已提交过验收评分，visitServiceInfoId={}", visitServiceInfoId);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        ErpServiceAcceptance acceptance = new ErpServiceAcceptance();
        acceptance.setVisitInfoId(visitServiceInfoId);
        acceptance.setScore(requestDto.getScore());
        acceptance.setServiceGoal(requestDto.getServiceGoal());
        ErpVisitServiceInfo visitInfo = visitServiceInfoService.get(visitServiceInfoId);
        if (visitInfo == null) {
            logger.info("visitInfo 不存在！visitServiceInfoId={}", visitServiceInfoId);
            throw new ServiceException(CommonConstants.FailMsg.PARAM);
        }
        acceptance.setProcInsId(visitInfo.getProcInsId());
        this.save(acceptance);
        String id = acceptance.getId();
        // #### 服务验收及评分表数据处理 end ########

        // #### 问题列表数据处理 start ########
        List<AcceptanceQuestionRequestDto> questionDtoList = requestDto.getQuestionList();
        if (CollectionUtils.isNotEmpty(questionDtoList)) {
            ErpOrderSplitCommentQuestion question = null;
            for (AcceptanceQuestionRequestDto ques : questionDtoList) {
                question = new ErpOrderSplitCommentQuestion();
                question.setContent(ques.getQuetionContent());
                question.setType(ques.getQuetionType());
                question.setRank(ques.getRank());
                question.setScopeType(SplitCommentQuestionConstants.SCOPE_TYPE_SERVICE_ACCEPTANCE);
                question.setCommentId(id);
                splitQuestionService.save(question);
                String questionId = question.getId();
                // #### 回答列表数据处理 start ########
                List<AcceptanceAnswerRequestDto> answerDtoList = ques.getAnswerList();
                ErpOrderSplitCommentAnswer answer = null;
                if (CollectionUtils.isNotEmpty(answerDtoList)) {
                    for (AcceptanceAnswerRequestDto ans : answerDtoList) {
                        answer = new ErpOrderSplitCommentAnswer();
                        answer.setQuestionId(questionId);
                        answer.setContent(ans.getAnswerContent());
                        answer.setCheckFlag(ans.getCheckFlag());
                        answer.setRank(ans.getRank());
                        splitAnswerService.save(answer);
                    }
                }
                // #### 回答列表数据处理 end ########
            }
        }
        // #### 问题列表数据处理 end ########
        // #### 调用erp接口 触发消息通知 start ####
        Map<String, String> postParam = new HashMap<>();
        postParam.put("visitInfoId", visitServiceInfoId);
        String jsonStr = HttpUtil.sendHttpPostReqToServerByParams(ServiceMessageUrlConstants.ACCEPTANCE_COMMIT, postParam);
        JSONObject json = JSONObject.parseObject(jsonStr);
        String code = json.getString("code");
        String message = json.getString("message");
        if (!"0".equals(code)) {
            logger.error("触发erp接口 消息通知！message={}", message);
            throw new ServiceException(CommonConstants.FailMsg.SYSTEM);
        }
        // #### 调用erp接口 触发消息通知 end ####
        logger.info("commit end | id={}", id);
        return id;
    }

    /**
     * 根据上门信息id 获取 验收及评分 信息
     *
     * @param visitInfoId
     * @return
     * @date 2018年7月23日
     * @author linqunzhi
     */
    public ErpServiceAcceptance getByVisitId(String visitInfoId) {
        ErpServiceAcceptance acceptance = dao.getByVisitId(visitInfoId);
        return acceptance;
    }

}
