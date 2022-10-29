package cn.neud.neusurvey.survey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.ChoiceDao;
import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.survey.service.ChoiceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * choice
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class ChoiceServiceImpl extends CrudServiceImpl<ChoiceDao, ChoiceEntity, ChoiceDTO> implements ChoiceService {

    @Override
    public QueryWrapper<ChoiceEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<ChoiceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}