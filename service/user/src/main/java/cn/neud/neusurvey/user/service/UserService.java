package cn.neud.neusurvey.user.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.dto.user.UserEmailLoginDTO;
import cn.neud.neusurvey.dto.user.UserLoginDTO;
import cn.neud.neusurvey.dto.user.UserRegisterDTO;
import cn.neud.neusurvey.entity.user.UserLoginEntity;
import cn.neud.neusurvey.entity.user.UserEntity;

/**
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface UserService extends CrudService<UserEntity, UserDTO> {

    Result loginValidate(UserLoginDTO userLoginDTO);

    Result register(UserRegisterDTO userRegisterDTO);

    Result emailLoginValidate(UserEmailLoginDTO userEmailLoginDTO);
}