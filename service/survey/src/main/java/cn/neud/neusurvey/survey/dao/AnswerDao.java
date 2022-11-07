package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.AnswerEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyUserContent_AnswerItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * answer
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface AnswerDao extends BaseDao<AnswerEntity> {

    @Select("select survey_id,user_id,content from answer where question_id=#{id}")
    List<SurveyUserContent_AnswerItem> getSurveyUserContentByQuestionId(String id);
}