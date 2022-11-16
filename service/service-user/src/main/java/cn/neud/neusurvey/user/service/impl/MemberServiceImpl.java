package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.user.MemberEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.MemberDao;
import cn.neud.neusurvey.dto.user.MemberDTO;
import cn.neud.neusurvey.user.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * member
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class MemberServiceImpl extends CrudServiceImpl<MemberDao, MemberEntity, MemberDTO> implements MemberService {

    @Override
    public QueryWrapper<MemberEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public boolean have(String userId, String groupId) {
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("group_id", groupId);
        System.out.println(userId);
        System.out.println(groupId);
        List<MemberEntity> memberEntities = baseDao.selectList(wrapper);
        System.out.println(memberEntities);
        return memberEntities.size() != 0;
    }

}
