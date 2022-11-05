package cn.neud.neusurvey.survey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.ChooseDao;
import cn.neud.neusurvey.dto.survey.ChooseDTO;
import cn.neud.neusurvey.entity.survey.ChooseEntity;
import cn.neud.neusurvey.survey.service.ChooseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-05
 */
@Service
public class ChooseServiceImpl extends CrudServiceImpl<ChooseDao, ChooseEntity, ChooseDTO> implements ChooseService {

    @Override
    public QueryWrapper<ChooseEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<ChooseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}