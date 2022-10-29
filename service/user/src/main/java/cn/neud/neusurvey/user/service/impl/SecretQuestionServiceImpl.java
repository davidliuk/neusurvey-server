package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.user.dao.SecretQuestionDao;
import cn.neud.neusurvey.entity.user.SecretQuestionEntity;
import cn.neud.neusurvey.user.service.SecretQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.dto.user.SecretQuestionDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class SecretQuestionServiceImpl extends CrudServiceImpl<SecretQuestionDao, SecretQuestionEntity, SecretQuestionDTO> implements SecretQuestionService {

    @Override
    public QueryWrapper<SecretQuestionEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SecretQuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}