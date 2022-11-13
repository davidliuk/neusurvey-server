package cn.neud.neusurvey.user.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.user.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    //雷世鹏: 增加对成员的判定,供返回值使用
    @Select("select * from member where user_id=#{userId} and group_id=#{groupId}")
    MemberEntity selectByPrimaryKey(String userId,String groupId);

    void softDeleteByPrimary(String user_id, String group_id);

    void addGroupUser(String user_id, String group_id);

    @Select("select user_id from member where group_id=#{groupId}")
    String[] selectUserIdsByGroupId(@Param("groupId") String groupId);

    @Update("update member " +
            "set is_deleted = '0' " +
            "where user_id = #{user_id} " +
            "and group_id = #{group_id}")
    void activeUser(String user_id, String group_id);
}