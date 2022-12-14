package cn.neud.neusurvey.survey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.RelateDao;
import cn.neud.neusurvey.dto.survey.RelateDTO;
import cn.neud.neusurvey.entity.survey.RelateEntity;
import cn.neud.neusurvey.survey.service.RelateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-11
 */
@Service
public class RelateServiceImpl extends CrudServiceImpl<RelateDao, RelateEntity, RelateDTO> implements RelateService {

    @Override
    public QueryWrapper<RelateEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RelateEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}