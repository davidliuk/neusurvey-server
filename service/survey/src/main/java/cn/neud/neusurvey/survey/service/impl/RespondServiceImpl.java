package cn.neud.neusurvey.survey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.RespondDao;
import cn.neud.neusurvey.dto.survey.RespondDTO;
import cn.neud.neusurvey.entity.survey.RespondEntity;
import cn.neud.neusurvey.survey.service.RespondService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-09
 */
@Service
public class RespondServiceImpl extends CrudServiceImpl<RespondDao, RespondEntity, RespondDTO> implements RespondService {

    @Override
    public QueryWrapper<RespondEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RespondEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}