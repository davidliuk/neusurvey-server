package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.survey.service.AnswerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.AnswerDao;
import cn.neud.neusurvey.dto.survey.AnswerDTO;
import cn.neud.neusurvey.entity.survey.AnswerEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * answer
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class AnswerServiceImpl extends CrudServiceImpl<AnswerDao, AnswerEntity, AnswerDTO> implements AnswerService {

    @Override
    public QueryWrapper<AnswerEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AnswerEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}