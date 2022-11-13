package cn.neud.neusurvey.survey.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.AnsweredSurveyDTO;
import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.entity.survey.SurveyEntity;

/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface SurveyService extends CrudService<SurveyEntity, SurveyDTO> {

    boolean ifExists(String id);

    boolean ifDeleted(String id);

    Result deleteSurvey(String[] ids);

    Result deleteSurveyLogic(String[] ids);

    AnsweredSurveyDTO getUserAnswerDerail(String id, String userId);

    Result updateSurvey(SurveyDTO dto);

}