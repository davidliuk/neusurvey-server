package cn.neud.neusurvey.survey.service;

import cn.neud.common.service.CrudService;
import cn.neud.neusurvey.dto.survey.HaveDTO;
import cn.neud.neusurvey.entity.survey.HaveEntity;

/**
 * have
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface HaveService extends CrudService<HaveEntity, HaveDTO> {
    void delete(String surveyId, String questionId);
}