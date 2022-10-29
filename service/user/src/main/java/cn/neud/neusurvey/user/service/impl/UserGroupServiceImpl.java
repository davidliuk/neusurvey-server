package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.user.UserGroupEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.UserGroupDao;
import cn.neud.neusurvey.dto.user.UserGroupDTO;
import cn.neud.neusurvey.user.service.UserGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * user_group
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class UserGroupServiceImpl extends CrudServiceImpl<UserGroupDao, UserGroupEntity, UserGroupDTO> implements UserGroupService {

    @Override
    public QueryWrapper<UserGroupEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserGroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}