package cn.neud.neusurvey.survey.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.QuestionCreateChoiceDTO;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.survey.dao.ChoiceDao;
import cn.neud.neusurvey.survey.dao.QuestionDao;
import com.alibaba.nacos.common.utils.UuidUtils;
import cn.neud.neusurvey.dto.survey.*;
import cn.neud.neusurvey.entity.survey.*;
import cn.neud.neusurvey.mapper.survey.SurveyMapper;
import cn.neud.neusurvey.survey.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.SurveyDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class SurveyServiceImpl extends CrudServiceImpl<SurveyDao, SurveyEntity, SurveyDTO> implements SurveyService {

    @Resource
    HaveService haveService;

    @Resource
    GotoService gotoService;

    @Resource
    QuestionService questionService;

    @Resource
    ChoiceService choiceService;
    @Autowired
    QuestionDao questionDao;

    @Autowired
    ChoiceDao choiceDao;

    @Override
    public QueryWrapper<SurveyEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<SurveyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void save(SurveyDTO dto) {
        SurveyEntity survey = SurveyMapper.INSTANCE.toSurvey(dto);
        survey.setCreator(survey.getManagedBy());
        survey.setUpdater(survey.getManagedBy());
        survey.setCreateDate(new Date());
        survey.setUpdateDate(new Date());
        super.insert(survey);

        HaveEntity have = new HaveEntity();
        have.setSurveyId(survey.getId());
        GotoEntity goTo = new GotoEntity();
        goTo.setSurveyId(survey.getId());

        for (QuestionEntity question : survey.getQuestions()) {
            questionService.insert(question);
            have.setQuestionId(question.getId());
            have.setNextId(question.getNextId());
            goTo.setQuestionId(question.getId());
            haveService.insert(have);
            for (ChoiceEntity choice : question.getChoices()) {
                choiceService.insert(choice);
                goTo.setChoiceId(choice.getId());
                gotoService.insert(goTo);
            }
        }
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            SurveyEntity survey = SurveyMapper.INSTANCE.toSurvey(this.get(id));
            this.deleteById(survey.getId());

            for (QuestionEntity question : survey.getQuestions()) {
                questionService.deleteById(question.getId());
                haveService.delete(survey.getId(), question.getId());
                for (ChoiceEntity choice : question.getChoices()) {
                    choiceService.deleteById(choice.getId());
                    gotoService.delete(survey.getId(), choice.getId());
                }
            }
        }
    }

    @Override
    public void update(SurveyDTO dto) {
//        super.update(dto);
        this.delete(new String[]{dto.getId()});
        this.save(dto);
    }

    @Override
    public SurveyDTO get(String id) {
        // survey 基本内容
        SurveyDTO survey = SurveyMapper.INSTANCE.fromSurvey(super.selectById(id));
        // survey 所有题目及下一题信息
        Map<String, Object> map = new HashMap<>(1);
        map.put("surveyId", id);
        List<HaveDTO> questionList = haveService.list(map);
        String[] questionIds = new String[questionList.size()];
        Map<String, String> questionsMap = new HashMap<>();
        for (int i = 0; i < questionList.size(); i++) {
            questionIds[i] = questionList.get(i).getQuestionId();
            questionsMap.put(questionList.get(i).getQuestionId(), questionList.get(i).getNextId());
        }
        List<QuestionDTO> questions = questionService.in(questionIds);
        survey.setQuestions(questions);

        // survey 中所有选项跳转信息
        List<GotoDTO> goToList = gotoService.list(map);
        Map<String, String> choices = new HashMap<>();
        for (GotoDTO gotoDTO : goToList) {
            choices.put(gotoDTO.getChoiceId(), gotoDTO.getQuestionId());
        }

        Map<String, Object> choiceParams = new HashMap<>(1);
        for (QuestionDTO question : questions) {
            question.setNextId(questionsMap.get(question.getId()));
            choiceParams.put("belongTo", question.getId());
            List<ChoiceDTO> choiceList = choiceService.list(choiceParams);
            question.setChoices(choiceList);
            for (ChoiceDTO choice: choiceList) {
                choice.setGoTo(choices.get(choice.getId()));
            }
        }

        return survey;
    }
}
