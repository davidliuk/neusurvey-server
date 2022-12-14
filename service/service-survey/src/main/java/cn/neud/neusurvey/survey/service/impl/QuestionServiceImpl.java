package cn.neud.neusurvey.survey.service.impl;

import cn.neud.common.utils.ConvertUtils;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import cn.neud.neusurvey.dto.survey.QuestionCreateChoiceDTO;
import cn.neud.neusurvey.dto.survey.QuestionCreateDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.GotoEntity;
import cn.neud.neusurvey.entity.survey.QuestionInfoEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.ChoiceContent_ChoiceItem;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyChoice_GotoItem;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyNext_HaveItem;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyUserContent_AnswerItem;
import cn.neud.neusurvey.survey.dao.*;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.survey.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class QuestionServiceImpl extends CrudServiceImpl<QuestionDao, QuestionEntity, QuestionDTO> implements QuestionService {

    @Resource
    GotoDao gotoDao;

    @Resource
    ChoiceDao choiceDao;

    @Resource
    QuestionDao questionDao;

    @Resource
    HaveDao haveDao;

    @Resource
    AnswerDao answerDao;

    public List<QuestionDTO> in(String[] ids) {
        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
//        System.out.println(ids[0] + ids[1]);
        wrapper.in(ObjectUtils.isNotEmpty(ids), "id", ids);
        List<QuestionEntity> entityList = questionDao.selectList(wrapper);
        System.out.println(entityList);
        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    @Override
    public QueryWrapper<QuestionEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String stem = (String) params.get("stem");
        String questionType = (String) params.get("questionType");

        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(id), "id", id);
        wrapper.like(StringUtils.isNotBlank(stem), "stem", stem);
        wrapper.eq(StringUtils.isNotBlank(questionType), "questionType", questionType);

        return wrapper;
    }

    public int createQuestion(String userId, QuestionCreateDTO questionCreateDTO) {

//        BeanUtil.copyProperties();
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setStem(questionCreateDTO.getStem());
        questionEntity.setQuestionType(questionCreateDTO.getQuestionType());
        questionEntity.setId(questionCreateDTO.getId());
        questionEntity.setIsDeleted(0);
//        System.out.println(questionCreateDTO);
        System.out.println(questionCreateDTO.getChoices());

        for (int i = 0; i < questionCreateDTO.getChoices().size(); i++) {
            QuestionCreateChoiceDTO q = questionCreateDTO.getChoices().get(i);
            ChoiceEntity choiceEntity = new ChoiceEntity();
            GotoEntity gotoEntity = new GotoEntity();

            //??????????????????
            choiceEntity.setId(q.getId());
            choiceEntity.setContent(q.getContent());
            choiceEntity.setCreateDate(new Date(System.currentTimeMillis()));
            choiceEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            choiceEntity.setUpdater(userId);
            choiceEntity.setCreator(userId);
            choiceEntity.setBelongTo(userId);
            choiceEntity.setIsDeleted("0");//????????????
            choiceEntity.setChoiceOrder(i);


            if (choiceDao.selectById(choiceEntity.getId()) == null) {
                choiceDao.insert(choiceEntity);
            } else {
                return 444;
            }

        }

        questionEntity.setCreateDate(new Date(System.currentTimeMillis()));
        questionEntity.setCreator(userId);
        questionEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        questionEntity.setUpdater(userId);
        questionEntity.setIsDeleted(0);
        if (questionDao.selectById(questionEntity.getId()) == null) {
            questionDao.insert(questionEntity);
            return 111;
        } else {
            return 444;
        }
    }

    @Override
    public Result updateQuestion(QuestionDTO dto, String updaterId) {

        Result result = new Result();
        boolean ifOK = true;


        //?????????????????????
        QuestionEntity questionEntity = questionDao.selectById(dto.getId());
        if (questionEntity == null) return new Result().error("??????????????????????????????id");

        /*
        ????????????:
        ??????????????????
        ????????????question??????,id??????UUID,??????????????????,??????Updater
        ??????ArrayList?????????????????????Choice??????

        ??????:???????????????????????????????????????,?????????????????????result??????,?????????????????????flag??????????????????
        */

        //??????Question??????

        questionEntity.setStem(dto.getStem());
        questionEntity.setQuestionType(dto.getQuestionType());
        questionEntity.setUpdater(updaterId);
        questionEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        questionDao.updateById(questionEntity);

        //??????Choice??????
        ArrayList<ChoiceDTO> choiceDTOS = (ArrayList<ChoiceDTO>) dto.getChoices();
        String msg = new String();
        for (int i = 0; i < choiceDTOS.size(); i++) {
            ChoiceDTO tempDTO = choiceDTOS.get(i);

            //choice?????????????????????
            ChoiceEntity choiceEntity = choiceDao.selectById(tempDTO.getId());
            if (choiceEntity == null) {
                ifOK &= false;
                msg += "?????????id???" + tempDTO.getId() + "???choice??????\n";
                continue;
            }

            //????????????choice??????
            choiceEntity.setContent(tempDTO.getContent());
            choiceEntity.setBelongTo(questionEntity.getId());
            choiceEntity.setUpdater(updaterId);
            choiceEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            choiceDao.updateById(choiceEntity);
        }

        if (ifOK) return result.ok(null);
        else return result.error(msg + "???????????????????????????");
    }

    @Override
    public Result getQuestion(String[] ids) {
        Result<List<QuestionInfoEntity>> result=new Result();
        List<QuestionInfoEntity> resEntities=new ArrayList<>();

        for(int i=0;i< ids.length;i++)
        {
            QuestionInfoEntity tempEntity=new QuestionInfoEntity();

            QuestionEntity questionEntity=questionDao.selectById(ids[i]);
            if(questionEntity==null)
                return result.error("?????????id???"+ids[i]+"?????????");
            tempEntity.setId(ids[i]);
            tempEntity.setStem(questionEntity.getStem());
            tempEntity.setQuestionType(questionEntity.getQuestionType());
            tempEntity.setNote(questionEntity.getNote());
            tempEntity.setReserved(questionEntity.getReserved());

            List<SurveyNext_HaveItem> haveItems=haveDao.getSurveyNextByQuestionId(ids[i]);
            tempEntity.setHaveItems(haveItems);

            List<SurveyChoice_GotoItem> gotoItems=gotoDao.getSurveyChoiceByQuestionId(ids[i]);
            tempEntity.setGotoItems(gotoItems);

            List<ChoiceContent_ChoiceItem> choiceItems=choiceDao.getChoiceContentByQuestionId(ids[i]);
            tempEntity.setChoiceItems(choiceItems);

            List<SurveyUserContent_AnswerItem> answerItems=answerDao.getSurveyUserContentByQuestionId(ids[i]);
            tempEntity.setAnswerItems(answerItems);

            resEntities.add(tempEntity);
        }

        return result.ok(resEntities);
    }


    @Override
    public Result deleteQuestion(String[] ids) {

        Result result = new Result();

        boolean ifOK = true;
        String msg = new String();

        for (int i = 0; i < ids.length; i++) {
            QuestionEntity questionEntity = questionDao.selectById(ids[i]);

            if (questionEntity == null) {
                ifOK &= false;
                msg += "?????????id???" + ids[i] + "???question??????\n";
                continue;
            }
//
//            questionEntity.setUpdateDate(new Date(System.currentTimeMillis()));
//            questionEntity.setIsDeleted(1);
//
//            questionDao.updateById(questionEntity);
//
            questionDao.deleteById(questionEntity);
        }

        if (ifOK) return result.ok(null);
        else return result.error(msg + "???????????????????????????");
    }
}