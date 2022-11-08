package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.ChoiceContent_ChoiceItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * choice
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface ChoiceDao extends BaseDao<ChoiceEntity> {

    @Select("select id,content,question_id as go_to from choice left join goto on id=choice_id where belong_to=#{id}")
    List<ChoiceContent_ChoiceItem> getChoiceContentByQuestionId(String id);
}