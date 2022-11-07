package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.HaveEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyNext_HaveItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * have
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface HaveDao extends BaseDao<HaveEntity> {

    @Select("select survey_id,next_id from have where question_id=#{id}")
    List<SurveyNext_HaveItem> getSurveyNextByQuestionId(String id);
}