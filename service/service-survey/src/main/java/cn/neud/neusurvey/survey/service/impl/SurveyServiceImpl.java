package cn.neud.neusurvey.survey.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyUserContent_AnswerItem;
import cn.neud.neusurvey.survey.dao.*;
import cn.neud.neusurvey.dto.survey.*;
import cn.neud.neusurvey.entity.survey.*;
import cn.neud.neusurvey.mapper.survey.SurveyMapper;
import cn.neud.neusurvey.survey.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    QuestionDao questionDao;

    @Resource
    ChoiceDao choiceDao;

    @Resource
    AnswerDao answerDao;

    @Resource
    ChooseDao chooseDao;

    @Resource
    SurveyDao surveyDao;

    @Override
    public QueryWrapper<SurveyEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String name = (String) params.get("name");
        String description = (String) params.get("description");
        String managedBy = (String) params.get("managedBy");
        String typeId = (String) params.get("typeId");

        QueryWrapper<SurveyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(managedBy), "managedBy", managedBy);
        wrapper.eq(StringUtils.isNotBlank(typeId), "typeId", typeId);
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        wrapper.like(StringUtils.isNotBlank(description), "description", description);

        return wrapper;
    }

    @Override
    public void save(SurveyDTO dto) {
        SurveyEntity survey = SurveyMapper.INSTANCE.toSurvey(dto);
        survey.setCreator(survey.getManagedBy());
//        survey.setUpdater(survey.getManagedBy());
        survey.setCreateDate(new Date());
        survey.setUpdateDate(new Date());
        super.insert(survey);

        HaveEntity have = new HaveEntity();
        have.setSurveyId(survey.getId());
        GotoEntity goTo = new GotoEntity();
        goTo.setSurveyId(survey.getId());

        for (QuestionEntity question : survey.getQuestions()) {
            if (questionService.get(question.getId()) != null) {
                continue;
            }
            questionService.insert(question);
            have.setQuestionId(question.getId());
            have.setNextId(question.getNextId());
            haveService.insert(have);
            List<ChoiceEntity> choices = question.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                ChoiceEntity choice = choices.get(i);
                choice.setBelongTo(question.getId());
                choice.setChoiceOrder(i);
                choiceService.insert(choice);
                if (StringUtils.isNotBlank(choice.getGoTo())) {
                    goTo.setQuestionId(choice.getGoTo());
                    goTo.setChoiceId(choice.getId());
                    gotoService.insert(goTo);
                }
            }
        }
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            SurveyEntity survey = SurveyMapper.INSTANCE.toSurvey(this.get(id));
            this.deleteById(survey.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("surveyId", survey.getId());
            haveService.delete(map);
            gotoService.delete(map);
        }
    }

    @Override
    public void update(SurveyDTO dto) {
//        super.update(dto);

        this.delete(new String[]{dto.getId()});
        this.save(dto);
    }

//
//    public Result updateSurvey(SurveyDTO dto) {
//        Result result = new Result();
////        super.update(dto);
//        SurveyEntity surveyEntity = surveyDao.selectById(dto.getId());
//        ;
//
//        if ( surveyEntity.getStartTime().getTime() > System.currentTimeMillis()
//                &&surveyEntity.getEndTime().getTime() < System.currentTimeMillis())
//        {
//            result.setMsg("问卷正在进行，请稍后操作");
//            return result;
//        }
//        this.delete(new String[]{dto.getId()});
//        this.save(dto);
//        result.setMsg("修改成功");
//        return result;
//    }

    @Override
    public SurveyDTO get(String id) {
        // survey 基本内容
        SurveyDTO survey = SurveyMapper.INSTANCE.fromSurvey(super.selectById(id));
        System.out.println(survey);
        // survey 所有题目及下一题信息
        Map<String, Object> map = new HashMap<>(1);
        map.put("surveyId", id);
        List<HaveDTO> questionList = haveService.list(map);

        if (questionList.size() == 0) {
            return survey;
        }
        Set<String> allQuestion = new HashSet<>();
        Set<String> otherQuestion = new HashSet<>();
        String[] questionIds = new String[questionList.size()];
        Map<String, String> questionsMap = new HashMap<>();
        for (int i = 0; i < questionList.size(); i++) {
            questionIds[i] = questionList.get(i).getQuestionId();
            allQuestion.add(questionIds[i]);
            otherQuestion.add(questionList.get(i).getNextId());
            questionsMap.put(questionList.get(i).getQuestionId(), questionList.get(i).getNextId());
        }

        // survey 中所有选项跳转信息
        List<GotoDTO> goToList = gotoService.list(map);
        Map<String, String> choices = new HashMap<>();
        for (GotoDTO gotoDTO : goToList) {
            choices.put(gotoDTO.getChoiceId(), gotoDTO.getQuestionId());
            otherQuestion.add(gotoDTO.getQuestionId());
        }

        allQuestion.removeAll(otherQuestion);
        String rootId = (String) allQuestion.toArray()[0];
        List<QuestionDTO> questions = questionService.in(questionIds);
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(rootId)) {
                QuestionDTO root = questions.get(i);
                questions.set(i, questions.get(0));
                questions.set(0, root);
            }
        }
        survey.setQuestions(questions);

        // questions 所有的选项
        Map<String, Object> choiceParams = new HashMap<>(1);
        for (QuestionDTO question : questions) {
            question.setNextId(questionsMap.get(question.getId()));
            choiceParams.put("belongTo", question.getId());
            List<ChoiceDTO> choiceList = choiceService.list(choiceParams);
            question.setChoices(choiceList);
            for (ChoiceDTO choice : choiceList) {
                choice.setGoTo(choices.get(choice.getId()));
            }
        }

        return survey;
    }

    @Override
    public boolean ifExists(String id) {
        return surveyDao.selectById(id) != null;
    }

    @Override
    public boolean ifDeleted(String id) {
        return surveyDao.selectById(id).getIsDeleted() != null
                && surveyDao.selectById(id).getIsDeleted().equals("1");
    }

    @Override
    public Result deleteSurvey(String[] ids) {

        Result result = new Result();

        String msg = new String();

        for (int i = 0; i < ids.length; i++) {

            SurveyEntity surveyEntity = surveyDao.selectById(ids[i]);

            if (surveyDao.selectById(ids[i]) == null) {
                msg += "问卷" + ids[i] + "不存在\n";
                continue;
            }

            surveyDao.deleteById(surveyEntity);

        }
        result.setMsg(msg);
        return result.ok(null);
    }

    @Override
    public Result deleteSurveyLogic(String[] ids) {

        Result result = new Result();

        String msg = new String();

        for (int i = 0; i < ids.length; i++) {

            SurveyEntity surveyEntity = surveyDao.selectById(ids[i]);

            if (surveyDao.selectById(ids[i]) == null) {
                msg += "问卷" + ids[i] + "不存在\n";
                continue;
            }
//            surveyEntity.setUpdater("admin");
            surveyEntity.setIsDeleted("1");
            surveyEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            surveyDao.updateById(surveyEntity);
        }
        result.setMsg(msg);
        return result.ok(null);

    }

    @Override
    public AnsweredSurveyDTO getUserAnswerDerail(String id, String userId) {
        AnsweredSurveyDTO answeredSurveyDTO = new AnsweredSurveyDTO();



        // survey 基本内容
        SurveyDTO survey = SurveyMapper.INSTANCE.fromSurvey(super.selectById(id));

        // survey 所有题目及下一题信息
        Map<String, Object> map = new HashMap<>(1);
        map.put("surveyId", id);
        List<HaveDTO> questionList = haveService.list(map);
        System.out.println(questionList);

        if (questionList.size() == 0) {
            answeredSurveyDTO.setSurvey(survey);
            return answeredSurveyDTO;
        }

        Set<String> allQuestion = new HashSet<>();
        Set<String> otherQuestion = new HashSet<>();
        String[] questionIds = new String[questionList.size()];
        Map<String, String> questionsMap = new HashMap<>();

        for (int i = 0; i < questionList.size(); i++) {
            questionIds[i] = questionList.get(i).getQuestionId();
            allQuestion.add(questionIds[i]);
            otherQuestion.add(questionList.get(i).getNextId());
            questionsMap.put(questionList.get(i).getQuestionId(), questionList.get(i).getNextId());
        }

        allQuestion.removeAll(otherQuestion);
        String rootId = (String) allQuestion.toArray()[0];
        List<QuestionDTO> questions = questionService.in(questionIds);

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(rootId)) {
                QuestionDTO root = questions.get(i);
                questions.set(i, questions.get(0));
                questions.set(0, root);
            }
        }
        survey.setQuestions(questions);

        // survey 中所有选项跳转信息
        List<GotoDTO> goToList = gotoService.list(map);
        Map<String, String> choices = new HashMap<>();
        for (GotoDTO gotoDTO : goToList) {
            choices.put(gotoDTO.getChoiceId(), gotoDTO.getQuestionId());
        }

        List<AnswerDTO> answerList = new ArrayList<>();
        // questions 所有的选项
        Map<String, Object> choiceParams = new HashMap<>(1);

        for (QuestionDTO question : questions) {
            question.setNextId(questionsMap.get(question.getId()));
            choiceParams.put("belongTo", question.getId());
            List<ChoiceDTO> choiceList = choiceService.list(choiceParams);
            question.setChoices(choiceList);
            for (ChoiceDTO choice : choiceList) {
                choice.setGoTo(choices.get(choice.getId()));
            }

            //答题选项
            AnswerDTO answerDTO = new AnswerDTO();
            System.out.println(userId + id + question.getId());

            List<String> chooseList = chooseDao.selectByUserAndSurveyId(userId, id, question.getId());
            answerDTO.setQuestionId(question.getId());
            answerDTO.setSurveyId(id);
            answerDTO.setChoices(chooseList);
            if (chooseList == null || choiceList.size() == 0) {
                String content = answerDao.selectContentByByUserAndSurveyId(userId, id, question.getId());
                answerDTO.setContent(content);
            }
            answerList.add(answerDTO);

        }

        answeredSurveyDTO.setSurvey(survey);
        answeredSurveyDTO.setAnswerList(answerList);
        return answeredSurveyDTO;
    }

    @Override
    public Result updateSurvey(SurveyDTO dto) {

        SurveyEntity surveyEntity = surveyDao.selectById(dto.getId());

        if (surveyEntity == null)
            return new Result().error("没有找到该问卷");
        if (surveyEntity.getIsDeleted() != null
                && surveyEntity.getIsDeleted().equals("1"))
            return new Result().error("该问卷已经被删除");

        if (surveyEntity.getReserved() != null && surveyEntity.getReserved().equals("1"))
//        if ((surveyEntity.getStartTime() != null && System.currentTimeMillis() >= surveyEntity.getStartTime().getTime())
//                && (surveyEntity.getEndTime() != null && System.currentTimeMillis() <= surveyEntity.getEndTime().getTime()))
            return new Result().error("该问卷正在进行中,无法修改");

        super.update(dto);

        return new Result().ok(null);
    }

    @Override
    public Result getGroup(String id) {
        return new Result().ok(super.get(id).getUpdater());
    }

}
