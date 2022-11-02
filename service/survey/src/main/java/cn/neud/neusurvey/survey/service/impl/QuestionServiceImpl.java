package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.dto.survey.QuestionCreateChoiceDTO;
import cn.neud.neusurvey.dto.survey.QuestionCreateDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.GotoEntity;
import cn.neud.neusurvey.survey.dao.ChoiceDao;
import cn.neud.neusurvey.survey.dao.GotoDao;
import cn.neud.neusurvey.survey.dao.QuestionDao;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.survey.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class QuestionServiceImpl extends CrudServiceImpl<QuestionDao, QuestionEntity, QuestionDTO> implements QuestionService {

    @Autowired
    GotoDao gotoDao;

    @Autowired
    ChoiceDao choiceDao;

    @Autowired
    QuestionDao questionDao;

    @Override
    public QueryWrapper<QuestionEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    public int createQuestion(String userId, QuestionCreateDTO questionCreateDTO){

//        BeanUtil.copyProperties();
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setStem(questionCreateDTO.getStem());
        questionEntity.setQuestionType(questionCreateDTO.getQuestion_type());
        questionEntity.setId(questionCreateDTO.getId());
        questionEntity.setIsDeleted("0");
//        System.out.println(questionCreateDTO);
        System.out.println(questionCreateDTO.getChoices());

        for (int i = 0; i < questionCreateDTO.getChoices().size(); i++) {
            QuestionCreateChoiceDTO q = questionCreateDTO.getChoices().get(i);
            ChoiceEntity choiceEntity = new ChoiceEntity();
            GotoEntity gotoEntity = new GotoEntity();

            //设置选项属性
            choiceEntity.setId(q.getId());
            choiceEntity.setContent(q.getContent());
            choiceEntity.setCreateDate(new Date(System.currentTimeMillis()));
            choiceEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            choiceEntity.setUpdater("userId");
            choiceEntity.setCreator("userId");
            choiceEntity.setBelongTo("userId");
            choiceEntity.setIsDeleted("0");//后续修改
            choiceEntity.setChoiceOrder(i);
            choiceEntity.setReserved("");
            System.out.println(choiceEntity);

            //设置goto关系属性
            gotoEntity.setChoiceId(q.getId());
            gotoEntity.setQuestionId(questionEntity.getId());
            gotoEntity.setSurveyId("1");//后续修改
            gotoEntity.setCreator(userId);
            gotoEntity.setIsDeleted("0");//后续修改
            gotoEntity.setCreateDate(new Date(System.currentTimeMillis()));
            gotoEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            gotoEntity.setUpdater(userId);

            if (choiceDao.selectById(choiceEntity.getId()) == null){
                choiceDao.insert(choiceEntity);
            }else {
                return 444;
            }
            if (gotoDao.selectById(q.getId()) == null){
                gotoDao.insert(gotoEntity);
            }else {
               return 444;
            }

        }
        questionEntity.setCreateDate(new Date(System.currentTimeMillis()));
        questionEntity.setCreator(userId);
        questionEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        questionEntity.setUpdater(userId);
        questionEntity.setIsDeleted("0");
        if (questionDao.selectById(questionEntity.getId()) == null){
            questionDao.insert(questionEntity);
            return 111;
        }else {
            return 444;
        }
    }

}