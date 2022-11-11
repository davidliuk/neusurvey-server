package cn.neud.neusurvey.user.service;

import cn.neud.common.service.CrudService;
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
    Result sendCode(SendCodeDTO sendCodeDTO) ;
    Result retrieve(UserVerificationLoginDTO userVerificationLoginDTO) ;

    void add(SecretChangeDTO[] dtos);

    void update(SecretChangeDTO[] dtos);
}