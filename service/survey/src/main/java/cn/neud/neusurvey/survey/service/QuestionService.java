package cn.neud.neusurvey.survey.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.QuestionCreateDTO;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface QuestionService extends CrudService<QuestionEntity, QuestionDTO> {

    int createQuestion(String userId, QuestionCreateDTO questionCreateDTO);


    Result updateQuestion(QuestionDTO dto, String updaterId);
}