package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.survey.service.HaveService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.HaveDao;
import cn.neud.neusurvey.dto.survey.HaveDTO;
import cn.neud.neusurvey.entity.survey.HaveEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * have
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class HaveServiceImpl extends CrudServiceImpl<HaveDao, HaveEntity, HaveDTO> implements HaveService {

    @Override
    public QueryWrapper<HaveEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String surveyId = (String) params.get("surveyId");

        QueryWrapper<HaveEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(surveyId), "survey_id", surveyId);

        return wrapper;
    }
    
    @Override
    public void delete(String surveyId, String questionId) {
        QueryWrapper<HaveEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("survey_id", surveyId);
        wrapper.eq("question_id", questionId);
        baseDao.delete(wrapper);
    }
}
