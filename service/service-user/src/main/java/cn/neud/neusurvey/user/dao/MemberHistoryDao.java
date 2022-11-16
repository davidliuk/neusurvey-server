package cn.neud.neusurvey.user.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.AnswerEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyUserContent_AnswerItem;
import cn.neud.neusurvey.entity.user.MemberHistoryEntity;
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
public interface MemberHistoryDao extends BaseDao<MemberHistoryEntity> {

    @Select("select user_id from member_history where group_history_id=#{groupHistoryId}")
    String[] selectByGroupHistoryId(String groupHistoryId);

//    List<String> selectByGroupHistoryId(String groupHistoryId);
}