package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.user.GroupHistoryEntity;
import cn.neud.neusurvey.user.service.GroupHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.GroupHistoryDao;
import cn.neud.neusurvey.dto.user.GroupHistoryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class GroupHistoryServiceImpl extends CrudServiceImpl<GroupHistoryDao, GroupHistoryEntity, GroupHistoryDTO> implements GroupHistoryService {

    @Override
    public QueryWrapper<GroupHistoryEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<GroupHistoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

}
