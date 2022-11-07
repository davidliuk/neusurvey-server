package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.GotoEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyChoice_GotoItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * goto
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface GotoDao extends BaseDao<GotoEntity> {


    GotoEntity selectByPrimary(GotoEntity gotoEntity);

    @Select("select survey_id,choice_id from goto where question_id=#{id}")
    List<SurveyChoice_GotoItem> getSurveyChoiceByQuestionId(String id);
}