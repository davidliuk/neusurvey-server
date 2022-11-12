package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.entity.statistics.StatisticItemEntity;
import cn.neud.neusurvey.entity.user.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    @Select("select * from user where username=#{username};")
    UserEntity selectByUsername(String username);

    @Select("select * from user where mobile=#{mobile};")
    UserEntity selectByMobile(String mobile);

    UserEntity selectByEmail(String email);

    List<UserDTO> pageGroupUser(Map<String, Object> params);

    Integer countGroupUser(String group_id,String username);

    List<StatisticItemEntity> statisticHeatmap(String group_id);

    List<UserDTO> pageAnswerUser(Map<String, Object> params);

    Integer countAnswerUser();


    List<StatisticItemEntity> statisticByGender(String group_id);

    List<StatisticItemEntity> statisticByBirth(String group_id);

    List<StatisticItemEntity> statisticByJob(String group_id);

    @Select("select count(*)\n" +
            "        from respond\n" +
            "        where user_id = #{respond_id}")
    Integer countRespond(String respond_id);

    @Select("select (UNIX_TIMESTAMP(end_time) - UNIX_TIMESTAMP(start_time)) dif_second\n" +
            "        from respond\n" +
            "        where user_id = #{respond_id}")
    List<Integer> respondTime(String respond_id);

    @Select("select survey_type.name as name, count(*) as value,  count(*)  as percentage\n" +
            "from respond, survey, survey_type\n" +
            "where user_id = #{respond_id} and respond.survey_id = survey.id and survey.type_id = survey_type.id\n" +
            "group by survey.type_id")
    List<StatisticItemEntity> statisticSurveyType(String group_id);

    @Select("select survey.creator as name, count(*) as value,  count(*)  as percentage\n" +
            "from respond, survey\n" +
            "where respond.user_id = #{respond_id} and respond.survey_id = survey.id \n" +
            "group by survey.creator")
    List<StatisticItemEntity> statisticUserType(String group_id);
}