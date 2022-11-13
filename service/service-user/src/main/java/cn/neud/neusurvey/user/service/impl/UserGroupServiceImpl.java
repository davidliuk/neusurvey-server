package cn.neud.neusurvey.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.dto.user.UserGroupOperateUserDTO;
import cn.neud.neusurvey.entity.user.GroupHistoryEntity;
import cn.neud.neusurvey.entity.user.MemberEntity;
import cn.neud.neusurvey.entity.user.UserGroupEntity;
import cn.neud.neusurvey.user.dao.*;
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
    UserDao userDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public QueryWrapper<UserGroupEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserGroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }




    @Override
    public int deleteGroup(String[] ids) {
        String updater = "admin";
        for (int i = 0; i < ids.length; i++) {
            List<MemberEntity> memberEntityList = memberDao.selectByGroupId(ids[i]);
            //雷世鹏:两种情况下可以删除:一是member没有,二是有但全部已经软删除了
            boolean flag=memberEntityList.size()!=0;
            for(int j=0;j<memberEntityList.size();j++)
            {
                flag &= memberEntityList.get(j).getIsDeleted()!=null&&memberEntityList.get(j).getIsDeleted().equals("1");
            }

            if (flag){
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
            }else {
                return 444;
            }
        }
        return 111;


    }

    @Override
    public int updateGroup(UserGroupDTO dto) {
        List<MemberEntity> memberEntityList = memberDao.selectByGroupId(dto.getId());
        if (memberEntityList.size() == 0){
            UserGroupEntity userGroupEntity = userGroupDao.selectById(dto.getId());
            if (dto != null && userGroupEntity != null){
                BeanUtil.copyProperties(dto,userGroupEntity);
                System.out.println(userGroupEntity);
                userGroupDao.updateById(userGroupEntity);
            }
        }else {
            return 444;
        }
        return 111;
    }

    @Override
    public int deleteUserById(String id) {

        return 0;
    }

    @Override
    public Result deleteUserByPrimary(UserGroupOperateUserDTO dto) {
        String[] user_ids = dto.getUser_ids();

        Result result=new Result();
        String msg=new String();
        if(userGroupDao.selectById(dto.getGroup_id())==null)
            return result.error("没有这个群");
        if(userGroupDao.selectById(dto.getGroup_id()).getIsDeleted().equals("1"))
            return result.error("该群已经被删除");


        for (int i = 0; i < user_ids.length; i++) {
            if(memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id())==null)
            {
                msg+="群里没有找到id为"+user_ids[i]+"的用户\n";
                continue;
            }

            if(memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id()).getIsDeleted()!=null
                    &&memberDao.selectByPrimaryKey(user_ids[i], dto.getGroup_id()).getIsDeleted().equals("1"))
            {
                msg+="群里id为"+user_ids[i]+"的用户早已被删除了\n";
                continue;
            }

            memberDao.softDeleteByPrimary(user_ids[i],dto.getGroup_id());
        }

        result.setMsg(msg);

        return result.ok(null);
    }

    @Override
    public Result addGroupUser(UserGroupOperateUserDTO dto) {

        Result result=new Result();

        if(userGroupDao.selectById(dto.getGroup_id())==null)
            return result.error("没有这个群组");

        if(userGroupDao.selectById(dto.getGroup_id()).getIsDeleted()!=null
                &&userGroupDao.selectById(dto.getGroup_id()).getIsDeleted().equals("1"))
            return result.error("该群组已被删除");

        String[] user_ids = dto.getUser_ids();
        String msg=new String();
        for (int i = 0; i < user_ids.length; i++) {

            if(userDao.selectById(user_ids[i])==null)
            {
                msg+="没有id为"+user_ids[i]+"的用户\n";
                continue;
            }

            if(userDao.selectById(user_ids[i]).getIsDeleted()!=null
                    &&userDao.selectById(user_ids[i]).getIsDeleted().equals("1"))
            {
                msg+="用户"+user_ids[i]+"已注销\n";
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
//        System.out.println("body"+params);
        Integer page = Integer.parseInt(String.valueOf(params.get("page")));
        Integer size = Integer.parseInt((String) params.get("size"));
        //page从零开始
        params.put("page",page * size);
        params.put("size", size);
        String group_id = (String) params.get("group_id");
        String username = (String) params.get("username");
        if (username == null)
            username = "";
        List<UserDTO> list = userDao.pageGroupUser(params);
        Integer total = userDao.countGroupUser(group_id,username);
        PageData<UserDTO> pageData = new PageData<>(list,total);
        return pageData;
    }

    @Override
    public int countGroup(String id) {
        return 0;
    }



    @Override
    public Result addGroup(UserGroupDTO dto) {

        Result result=new Result();

        if(userGroupDao.selectById(dto.getId())!=null)
            return result.error("该id的群组已存在");

        //创建群组
        UserGroupEntity userGroupEntity=new UserGroupEntity();
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


        //创建归属关系
        String[] ids=dto.getUserIds();
        for(int i=0;i< ids.length;i++)
        {
            QueryWrapper<MemberEntity> tempQ=new QueryWrapper<MemberEntity>()
                    .eq("user_id",ids[i])
                    .eq("group_id",userGroupEntity.getId());
            if(memberDao.exists(tempQ))
                return result.error("该群组下已经存在id为"+ids[i]+"的答者");

            MemberEntity memberEntity=new MemberEntity();
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
        return userGroupDao.selectById(id)!=null;
    }

    @Override
    public boolean ifDeleted(String id) {
        return userGroupDao.selectById(id).getIsDeleted()!=null
                &&userGroupDao.selectById(id).getIsDeleted().equals("1");
    }

    @Override
    public Result updateGroupUsers(UserGroupOperateUserDTO dto) {
        Result result=new Result();

        if(userGroupDao.selectById(dto.getGroup_id())==null)
            return result.error("没有这个群组");

        if(userGroupDao.selectById(dto.getGroup_id()).getIsDeleted()!=null
                &&userGroupDao.selectById(dto.getGroup_id()).getIsDeleted().equals("1"))
            return result.error("该群组已被删除");

        String[] old_ids = memberDao.selectUserIdsByGroupId(dto.getGroup_id());
        UserGroupOperateUserDTO temp = new UserGroupOperateUserDTO();
        temp.setGroup_id(dto.getGroup_id());
        temp.setUser_ids(old_ids);
        temp.setCreator(dto.getCreator());

        deleteUserByPrimary(dto);

        String[] user_ids = dto.getUser_ids();
        String msg=new String();
        for (int i = 0; i < user_ids.length; i++) {

            if(userDao.selectById(user_ids[i])==null)
            {
                msg+="没有id为"+user_ids[i]+"的用户\n";
                continue;
            }

            if(userDao.selectById(user_ids[i]).getIsDeleted()!=null
                    &&userDao.selectById(user_ids[i]).getIsDeleted().equals("1"))
            {
                msg+="用户"+user_ids[i]+"已注销\n";
                continue;
            }
            if (memberDao.selectByPrimaryKey(user_ids[i],dto.getGroup_id()) != null){
                memberDao.activeUser(user_ids[i],dto.getGroup_id());
            }else {
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
        msg = "操作成功了哩^_^";
        result.setMsg(msg);
        return result.ok(null);
    }
}