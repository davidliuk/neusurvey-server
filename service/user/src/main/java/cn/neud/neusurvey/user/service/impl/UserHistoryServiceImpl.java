package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.user.UserHistoryEntity;
import cn.neud.neusurvey.user.service.UserHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.UserHistoryDao;
import cn.neud.neusurvey.dto.user.UserHistoryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class UserHistoryServiceImpl extends CrudServiceImpl<UserHistoryDao, UserHistoryEntity, UserHistoryDTO> implements UserHistoryService {

    @Override
    public QueryWrapper<UserHistoryEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserHistoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}