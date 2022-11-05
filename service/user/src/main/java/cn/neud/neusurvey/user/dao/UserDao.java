package cn.neud.neusurvey.user.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.dto.user.UserDTO;
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

    UserEntity selectByEmail(String email);

    List<UserDTO> pageGroupUser(Map<String, Object> params);

    Integer countGroupUser(Object group_id);
}