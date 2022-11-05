package cn.neud.neusurvey.user.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.user.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * member
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface MemberDao extends BaseDao<MemberEntity> {



    List<MemberEntity> selectByGroupId(String group_id);

    void softDeleteByPrimary(String user_id, String group_id);

    void addGroupUser(String user_id, String group_id);
}