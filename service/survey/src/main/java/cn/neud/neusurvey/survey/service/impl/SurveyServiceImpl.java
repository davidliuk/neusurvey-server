package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.entity.survey.SurveyEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.SurveyDao;
import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.survey.service.SurveyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class SurveyServiceImpl extends CrudServiceImpl<SurveyDao, SurveyEntity, SurveyDTO> implements SurveyService {

    @Override
    public QueryWrapper<SurveyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SurveyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}