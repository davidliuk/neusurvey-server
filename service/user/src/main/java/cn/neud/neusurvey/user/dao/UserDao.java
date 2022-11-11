package cn.neud.neusurvey.user.dao;

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

    void changePassword(Map<String, Object> params);

    List<StatisticItemEntity> statisticByGender(String group_id);

    List<StatisticItemEntity> statisticByBirth(String group_id);

    List<StatisticItemEntity> statisticByJob(String group_id);
}