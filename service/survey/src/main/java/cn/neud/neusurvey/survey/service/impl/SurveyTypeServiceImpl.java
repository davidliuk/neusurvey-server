package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.entity.survey.SurveyTypeEntity;
import cn.neud.neusurvey.survey.service.SurveyTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.SurveyTypeDao;
import cn.neud.neusurvey.dto.survey.SurveyTypeDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * survey_type
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class SurveyTypeServiceImpl extends CrudServiceImpl<SurveyTypeDao, SurveyTypeEntity, SurveyTypeDTO> implements SurveyTypeService {

    @Override
    public QueryWrapper<SurveyTypeEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SurveyTypeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}