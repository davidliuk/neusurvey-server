package cn.neud.neusurvey.survey.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.QuestionCreateChoiceDTO;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.survey.dao.ChoiceDao;
import cn.neud.neusurvey.survey.dao.QuestionDao;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.SurveyDao;
import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.survey.service.SurveyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class SurveyServiceImpl extends CrudServiceImpl<SurveyDao, SurveyEntity, SurveyDTO> implements SurveyService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    ChoiceDao choiceDao;

    @Override
    public QueryWrapper<SurveyEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<SurveyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}