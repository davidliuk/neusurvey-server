package cn.neud.neusurvey.user.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.SecretQuestionDTO;
import cn.neud.neusurvey.dto.user.SendCodeDTO;
import cn.neud.neusurvey.dto.user.UserPasswordResetDTO;
import cn.neud.neusurvey.dto.user.UserVerificationLoginDTO;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.user.SecretQuestionEntity;

import java.util.List;

/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface SecretQuestionService extends CrudService<SecretQuestionEntity, SecretQuestionDTO> {

    List<SecretQuestionDTO> list(String username);

    boolean saveSecret(SecretDTO dto);

    void add(String username, SecretChangeDTO[] dtos);

    void update(String username, SecretChangeDTO[] dtos);
    Result sendCode(SendCodeDTO sendCodeDTO) ;
    Result retrieve(UserVerificationLoginDTO userVerificationLoginDTO) ;
    Result reset(UserPasswordResetDTO userPasswordResetDTO) ;
}