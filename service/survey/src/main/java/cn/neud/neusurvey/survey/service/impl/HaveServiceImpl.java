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
    public QueryWrapper<HaveEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<HaveEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}