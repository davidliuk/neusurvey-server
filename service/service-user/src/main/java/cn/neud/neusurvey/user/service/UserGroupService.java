package cn.neud.neusurvey.user.service;

import cn.neud.common.page.PageData;
import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.dto.user.UserGroupDTO;
import cn.neud.neusurvey.dto.user.UserGroupOperateUserDTO;
import cn.neud.neusurvey.entity.user.UserGroupEntity;

import java.util.Map;

/**
 * user_group
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface UserGroupService extends CrudService<UserGroupEntity, UserGroupDTO> {

    int deleteGroup(String[] ids);

    int updateGroup(UserGroupDTO dto);

    int deleteUserById(String id);

    Result deleteUserByPrimary(UserGroupOperateUserDTO dto);

    Result addGroupUser(UserGroupOperateUserDTO dto);

    PageData<UserDTO> pageGroupUser(Map<String, Object> params);

    int countGroup(String id);

    Result addGroup(UserGroupDTO dto);

    boolean ifExists(String id);

    boolean ifDeleted(String id);

}