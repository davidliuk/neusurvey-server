package cn.neud.neusurvey.user.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.user.UserHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface UserHistoryDao extends BaseDao<UserHistoryEntity> {

    @Select("select * from user_history where update_date= (select max(update_date) from user_history where user_id=#{userId});")
    UserHistoryEntity selectTheLatest(String userId);
}