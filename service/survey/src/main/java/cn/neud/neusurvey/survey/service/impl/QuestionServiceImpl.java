package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.survey.dao.QuestionDao;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.survey.service.QuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class QuestionServiceImpl extends CrudServiceImpl<QuestionDao, QuestionEntity, QuestionDTO> implements QuestionService {

    @Override
    public QueryWrapper<QuestionEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}