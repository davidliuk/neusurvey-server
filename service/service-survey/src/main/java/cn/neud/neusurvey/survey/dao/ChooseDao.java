package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.ChooseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-05
 */
@Mapper
public interface ChooseDao extends BaseDao<ChooseEntity> {

    @Select("select c.choice_order " +
            "from choose ,choice c " +
            "where choose.user_id = #{userId} " +
            "and choose.survey_id = #{surveyId} " +
            "and choose.choice_id in " +
            "(select choice.id from choice where choice.belong_to = #{questionId})" +
            "and c.id = choose.choice_id")
    List<String> selectByUserAndSurveyId(@Param("userId") String userId,@Param("surveyId") String surveyId,@Param("questionId") String questionId);
}