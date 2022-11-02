package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.survey.dao.GotoDao;
import cn.neud.neusurvey.dto.survey.GotoDTO;
import cn.neud.neusurvey.entity.survey.GotoEntity;
import cn.neud.neusurvey.survey.service.GotoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * goto
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class GotoServiceImpl extends CrudServiceImpl<GotoDao, GotoEntity, GotoDTO> implements GotoService {

    @Override
    public QueryWrapper<GotoEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<GotoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void delete(String surveyId, String choiceId) {
        QueryWrapper<GotoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("survey_id", surveyId);
//        wrapper.eq("questionId", questionId);
        wrapper.eq("choice_id", choiceId);
        baseDao.delete(wrapper);
    }
}
