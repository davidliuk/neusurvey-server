package cn.neud.neusurvey.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.survey.InvitationDTO;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.dto.user.UserGroupOperateUserDTO;
import cn.neud.neusurvey.entity.user.GroupHistoryEntity;
import cn.neud.neusurvey.entity.user.MemberEntity;
import cn.neud.neusurvey.entity.user.MemberHistoryEntity;
import cn.neud.neusurvey.entity.user.UserGroupEntity;
import cn.neud.neusurvey.user.dao.*;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.dto.user.UserGroupDTO;
import cn.neud.neusurvey.user.service.UserGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * user_group
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class UserGroupServiceImpl extends CrudServiceImpl<UserGroupDao, UserGroupEntity, UserGroupDTO> implements UserGroupService {

    @Autowired
    UserGroupDao userGroupDao;

    @Autowired
    GroupHistoryDao groupHistoryDao;

    @Autowired
    MemberHistoryDao memberHistoryDao;

    @Autowired
    UserDao userDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public QueryWrapper<UserGroupEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String isDeleted = (String) params.get("isDeleted");

        QueryWrapper<UserGroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        if (isDeleted != null && isDeleted.equals("1"))
            wrapper.eq(StringUtils.isNotBlank(isDeleted), "is_deleted", isDeleted);
        if (isDeleted != null && isDeleted.equals("0"))
            wrapper.ne("is_deleted", "1");

        return wrapper;
    }


    @Override
    public int deleteGroup(String[] ids) {
        String updater = "admin";
        for (int i = 0; i < ids.length; i++) {
            List<MemberEntity> memberEntityList = memberDao.selectByGroupId(ids[i]);

            //?????????:???????????????????????????:??????member??????,????????????????????????????????????
            boolean flag = memberEntityList.size() != 0;
            for (int j = 0; j < memberEntityList.size(); j++) {
                flag &= memberEntityList.get(j).getIsDeleted() != null
                        && memberEntityList.get(j).getIsDeleted().equals("1");
            }

            if (flag || memberEntityList.size() == 0) {
                UserGroupEntity userGroupEntity = userGroupDao.selectById(ids[i]);
                userGroupEntity.setIsDeleted("1");
                userGroupDao.updateById(userGroupEntity);

                GroupHistoryEntity groupHistoryEntity = new GroupHistoryEntity();
                groupHistoryEntity.setGroupId(userGroupEntity.getId());
                groupHistoryEntity.setIsDeleted("0");
                groupHistoryEntity.setCreator(updater);
                groupHistoryEntity.setCreateDate(new Date(System.currentTimeMillis()));
                groupHistoryEntity.setUpdater(updater);
                groupHistoryEntity.setUpdateDate(new Date(System.currentTimeMillis()));
                groupHistoryDao.insert(groupHistoryEntity);


                //????????????
                String group_history_id = groupHistoryEntity.getId();
                String group_id = groupHistoryEntity.getGroupId();
                for (int j = 0; j < memberEntityList.size(); j++) {
                    //?????????memberHistory??????
                    String user_id = memberEntityList.get(j).getUserId();
                    MemberHistoryEntity memberHistoryEntity = new MemberHistoryEntity();
                    BeanUtil.copyProperties(memberEntityList.get(j), memberHistoryEntity);
                    memberHistoryEntity.setGroupHistoryId(group_history_id);
                    memberHistoryEntity.setUserId(user_id);
                    memberHistoryEntity.setUpdateDate(new Date(System.currentTimeMillis()));
                    memberHistoryDao.insert(memberHistoryEntity);

                    //???member????????????
                    memberDao.deleteByPrimaryKey(user_id, group_id);
                }


            } else {
                return 444;
            }
        }
        return 111;


    }

    @Override
    public Result updateGroup(UserGroupDTO dto) {


        Result result = new Result();

        UserGroupEntity userGroupEntity = userGroupDao.selectById(dto.getId());
        if (userGroupEntity == null) {
            return result.error("??????????????????");
        }
        if (userGroupEntity.getIsDeleted() != null
                && userGroupEntity.getIsDeleted().equals("1")) {
            return result.error("?????????????????????");
        }

        //????????????
        String creator = userGroupEntity.getCreator();
        BeanUtil.copyProperties(dto, userGroupEntity);
        UserGroupEntity copyUserGroupEntity = userGroupEntity;
        userGroupEntity.setCreator(creator);
        userGroupEntity.setUpdater(dto.getCreator());
        userGroupEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        userGroupDao.updateById(userGroupEntity);

        //????????????
        GroupHistoryEntity groupHistoryEntity = new GroupHistoryEntity();
        BeanUtil.copyProperties(copyUserGroupEntity, groupHistoryEntity);
        groupHistoryEntity.setId(UuidUtils.generateUuid());
        groupHistoryEntity.setGroupId(dto.getId());
        groupHistoryDao.insert(groupHistoryEntity);

        //????????????
        List<MemberEntity> memberEntityList = memberDao.selectByGroupId(dto.getId());
        String group_history_id = groupHistoryEntity.getId();
        String group_id = groupHistoryEntity.getGroupId();
        for (int i = 0; i < memberEntityList.size(); i++) {
            //?????????memberHistory??????
            String user_id = memberEntityList.get(i).getUserId();
            MemberHistoryEntity memberHistoryEntity = new MemberHistoryEntity();
            BeanUtil.copyProperties(memberEntityList.get(i), memberHistoryEntity);
            memberHistoryEntity.setGroupHistoryId(group_history_id);
            memberHistoryEntity.setUserId(user_id);
            memberHistoryEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            memberHistoryDao.insert(memberHistoryEntity);

            //???member????????????
            memberDao.deleteByPrimaryKey(user_id, group_id);
        }

        String[] ids = dto.getUserIds();

        for (int i = 0; i < ids.length; i++) {
            MemberEntity memberEntity = new MemberEntity();
            BeanUtil.copyProperties(groupHistoryEntity, memberEntity);
            memberEntity.setUserId(ids[i]);
            memberEntity.setGroupId(group_id);

            memberDao.insert(memberEntity);
        }

        return new Result().ok(null);
    }

    @Override
    public Result recoverUserHistory(String[] ids) {

        Result result = new Result();
        String msg = new String();
        boolean ifSuccess = true;

        //??????????????????????????????????????????usergroupDTO)?????????????????????????????????
        //?????????????????????????????????????????????????????????


        for (int i = 0; i < ids.length; i++) {

            GroupHistoryEntity groupHistoryEntity = groupHistoryDao.selectById(ids[i]);
            if (groupHistoryEntity == null) {
                msg += "?????????id???" + ids[i] + "???????????????\n";
                ifSuccess = false;
                continue;
            }
            if (groupHistoryEntity.getIsDeleted() != null
                    && groupHistoryEntity.getIsDeleted().equals("1")) {
                msg += "id???" + ids[i] + "???????????????????????????\n";
                ifSuccess = false;
                continue;
            }


            UserGroupDTO userGroupDTO = new UserGroupDTO();
            BeanUtil.copyProperties(groupHistoryEntity, userGroupDTO);
            userGroupDTO.setId(groupHistoryEntity.getGroupId());
            //??????member
//            List<String> memberIds=memberHistoryDao.selectByGroupHistoryId(groupHistoryEntity.getGroupId());
//            userGroupDTO.setUserIds(memberIds.toArray(new String[]{}));
            String[] memberIds = memberHistoryDao.selectByGroupHistoryId(groupHistoryEntity.getId());
            userGroupDTO.setUserIds(memberIds);

            UserGroupEntity userGroupEntity = userGroupDao.selectById(groupHistoryEntity.getGroupId());
            if (userGroupEntity == null) addGroup(userGroupDTO);
            else updateGroup(userGroupDTO);


        }


        if (ifSuccess) return result.ok(null);
        else return result.error(msg);

    }

    @Override
    public Result recoverFromDelete(String[] ids) {


        Result result = new Result();
        String msg = new String();
        boolean ifSuccess = true;

        //??????????????????????????????????????????usergroupDTO)?????????????????????????????????
        //?????????????????????????????????????????????????????????


        for (int i = 0; i < ids.length; i++) {

            UserGroupEntity userGroupEntity = userGroupDao.selectById(ids[i]);
            if (userGroupEntity == null) {
                msg += "?????????id???" + ids[i] + "?????????\n";
                ifSuccess = false;
                continue;
            }

            userGroupEntity.setIsDeleted("0");
            userGroupDao.deleteById(ids[i]);
            userGroupDao.insert(userGroupEntity);

        }


        if (ifSuccess) return result.ok(null);
        else return result.error(msg);


    }

    @Override
    public int deleteUserById(String id) {

        return 0;
    }

    @Override
    public Result deleteUserByPrimary(UserGroupOperateUserDTO dto) {
        String[] user_ids = dto.getUser_ids();

        Result result = new Result();
        String msg = new String();
        if (userGroupDao.selectById(dto.getGroup_id()) == null)
            return result.error("???????????????");
        if (userGroupDao.selectById(dto.getGroup_id()).getIsDeleted().equals("1"))
            return result.error("?????????????????????");


        for (int i = 0; i < user_ids.length; i++) {
            if (memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id()) == null) {
                msg += "??????????????????id???" + user_ids[i] + "?????????\n";
                continue;
            }

            if (memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id()).getIsDeleted() != null
                    && memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id()).getIsDeleted().equals("1")) {
                msg += "??????id???" + user_ids[i] + "???????????????????????????\n";
                continue;
            }

            memberDao.softDeleteByPrimary(user_ids[i], dto.getGroup_id());
        }

        result.setMsg(msg);

        return result.ok(null);
    }

    @Override
    public Result addGroupUser(UserGroupOperateUserDTO dto) {

        Result result = new Result();

        if (userGroupDao.selectById(dto.getGroup_id()) == null)
            return result.error("??????????????????");

        if (userGroupDao.selectById(dto.getGroup_id()).getIsDeleted() != null
                && userGroupDao.selectById(dto.getGroup_id()).getIsDeleted().equals("1"))
            return result.error("?????????????????????");

        String[] user_ids = dto.getUser_ids();
        String msg = new String();
        for (int i = 0; i < user_ids.length; i++) {

            if (userDao.selectById(user_ids[i]) == null) {
                msg += "??????id???" + user_ids[i] + "?????????\n";
                continue;
            }

            if (userDao.selectById(user_ids[i]).getIsDeleted() != null
                    && userDao.selectById(user_ids[i]).getIsDeleted().equals("1")) {
                msg += "??????" + user_ids[i] + "?????????\n";
                continue;
            }

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setGroupId(dto.getGroup_id());
            memberEntity.setUserId(user_ids[i]);
            memberEntity.setCreator(dto.getCreator());
            memberEntity.setCreateDate(new Date(System.currentTimeMillis()));
            memberEntity.setUpdater(dto.getCreator());
            memberEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            memberDao.insert(memberEntity);
        }

        result.setMsg(msg);
        return result.ok(null);
    }

    @Override
    public PageData<UserDTO> pageGroupUser(Map<String, Object> params) {
        Integer page = Integer.parseInt(String.valueOf(params.get("page")));
        Integer size = Integer.parseInt((String) params.get("size"));
        //page????????????
        params.put("page", page * size);
        params.put("size", size);
        String group_id = (String) params.get("group_id");
        String username = (String) params.get("username");
        if (username == null) {
            username = "";
        }
        List<UserDTO> list = userDao.pageGroupUser(params);

        Integer total = userDao.countGroupUser(group_id, username);
        PageData<UserDTO> pageData = new PageData<>(list, total);
        return pageData;
    }

    @Override
    public int countGroup(String id) {
        return 0;
    }

    @Override
    public Result addGroup(UserGroupDTO dto) {

        Result result = new Result();

        if (userGroupDao.selectById(dto.getId()) != null)
            return result.error("???id??????????????????");

        //????????????
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setId(dto.getId());
        userGroupEntity.setGroupname(dto.getGroupname());
        userGroupEntity.setDescription(dto.getDescription());
        userGroupEntity.setAvatar(dto.getAvatar());
        userGroupEntity.setCreator(dto.getCreator());
        userGroupEntity.setCreateDate(new Date((System.currentTimeMillis())));
        userGroupEntity.setUpdater(dto.getCreator());
        userGroupEntity.setUpdateDate(new Date((System.currentTimeMillis())));
        userGroupEntity.setIsDeleted("0");
        userGroupDao.insert(userGroupEntity);


        //??????????????????
        String[] ids = dto.getUserIds();
        for (int i = 0; i < ids.length; i++) {
            QueryWrapper<MemberEntity> tempQ = new QueryWrapper<MemberEntity>()
                    .eq("user_id", ids[i])
                    .eq("group_id", userGroupEntity.getId());
            if (memberDao.exists(tempQ))
                return result.error("????????????????????????id???" + ids[i] + "?????????");

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setUserId(ids[i]);
            memberEntity.setGroupId(userGroupEntity.getId());
            memberEntity.setCreator(userGroupEntity.getCreator());
            memberEntity.setCreateDate(new Date((System.currentTimeMillis())));
            memberEntity.setUpdater(userGroupEntity.getCreator());
            memberEntity.setUpdateDate(new Date((System.currentTimeMillis())));
            memberEntity.setIsDeleted("0");
            memberDao.insert(memberEntity);
        }


        return result.ok(null);
    }

    @Override
    public boolean ifExists(String id) {
        return userGroupDao.selectById(id) != null;
    }

    @Override
    public boolean ifDeleted(String id) {
        return userGroupDao.selectById(id).getIsDeleted() != null
                && userGroupDao.selectById(id).getIsDeleted().equals("1");
    }

    @Override
    public Result updateGroupUsers(UserGroupOperateUserDTO dto) {
        Result result = new Result();

        if (userGroupDao.selectById(dto.getGroup_id()) == null)
            return result.error("??????????????????");

        if (userGroupDao.selectById(dto.getGroup_id()).getIsDeleted() != null
                && userGroupDao.selectById(dto.getGroup_id()).getIsDeleted().equals("1"))
            return result.error("?????????????????????");

        String[] old_ids = memberDao.selectUserIdsByGroupId(dto.getGroup_id());
        UserGroupOperateUserDTO temp = new UserGroupOperateUserDTO();
        temp.setGroup_id(dto.getGroup_id());
        temp.setUser_ids(old_ids);
        temp.setCreator(dto.getCreator());

        deleteUserByPrimary(dto);

        String[] user_ids = dto.getUser_ids();
        String msg = new String();
        for (int i = 0; i < user_ids.length; i++) {

            if (userDao.selectById(user_ids[i]) == null) {
                msg += "??????id???" + user_ids[i] + "?????????\n";
                continue;
            }

            if (userDao.selectById(user_ids[i]).getIsDeleted() != null
                    && userDao.selectById(user_ids[i]).getIsDeleted().equals("1")) {
                msg += "??????" + user_ids[i] + "?????????\n";
                continue;
            }
            if (memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id()) != null) {
                memberDao.activeUser(user_ids[i], dto.getGroup_id());
            } else {
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setGroupId(dto.getGroup_id());
                memberEntity.setUserId(user_ids[i]);
                memberEntity.setCreator(dto.getCreator());
                memberEntity.setCreateDate(new Date(System.currentTimeMillis()));
                memberEntity.setUpdater(dto.getCreator());
                memberEntity.setUpdateDate(new Date(System.currentTimeMillis()));
                memberEntity.setIsDeleted("0");
                memberDao.insert(memberEntity);
            }
        }
        msg = "??????????????????^_^";
        result.setMsg(msg);
        return result.ok(null);
    }

    @Override
    public Result invitationCodeAddGroup(InvitationDTO dto) {
        Result result = new Result();

        String groupId = userGroupDao.selectByReserve(dto.getInvitationCode());
        if (groupId == null) {
            return result.error(404, "??????????????????????????????????????????????????????????????????");
        }
        MemberEntity m = memberDao.selectByPrimaryKey(dto.getUserId(), groupId);
        if (m != null) {
            System.out.println(m);
            if (m.getIsDeleted().equals("1")) {
                m.setIsDeleted("0");
                memberDao.activeUser(m.getUserId(), groupId);
                result.setCode(200);
                result.setMsg("????????????");
                return result;
            } else {
                return result.error(555, "?????????????????????????????????????????????");
            }
        }

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setGroupId(groupId);
        memberEntity.setUserId(groupId);
        memberEntity.setCreator(dto.getCreator());
        memberEntity.setCreateDate(new Date(System.currentTimeMillis()));
        memberEntity.setUpdater(dto.getCreator());
        memberEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        memberEntity.setIsDeleted("0");
        memberDao.insert(memberEntity);
        result.setCode(200);
        result.setMsg("????????????");

        return result;
    }

}