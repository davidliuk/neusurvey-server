package cn.neud.neusurvey.user.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.neusurvey.entity.user.UserLoginEntity;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.user.controller.UserController;

/**F
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface UserService extends CrudService<UserEntity, UserDTO> {

    Result loginValidate(UserLoginDTO userLoginDTO);

    Result register(UserRegisterDTO userRegisterDTO);

    Result codeLoginValidate(UserVerificationLoginDTO userVerificationLoginDTO) ;

    Result sendCode(SendCodeDTO sendCodeDTO) ;
    Result deleteLogic(String[] ids);

    Result updateUser(UserDTO dto);

    Result emailLoginValidate(UserEmailDTO userEmailDTO);

    Result recoverUser(String[] ids);

    Result recoverFromDelete(String[] ids);

}