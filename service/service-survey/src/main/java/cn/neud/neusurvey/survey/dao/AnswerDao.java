package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.AnswerEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyUserContent_AnswerItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    @Select("select content " +
            "from answer " +
            "where survey_id = #{surveyId} " +
            "and user_id = #{userId} " +
            "and question_id = #{questionId}")
    String selectContentByByUserAndSurveyId(@Param("userId") String userId,@Param("surveyId") String surveyId, @Param("questionId") String questionId);

    @Select("select count(user_id) from answer where survey_id=#{surveyId} AND question_id=#{questionId}")
    Integer getQuestionTotal(String surveyId, String questionId);

    @Select("select count(distinct user_id) from answer where survey_id=#{surveyId}")
    Integer getQuestionnaireTotal(String surveyId);

    @Select("select distinct user_id from answer where survey_id=#{surveyId}")
    List<String> getuserIdBySurveyId(String surveyId);
}