package cn.neud.neusurvey.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.dto.user.UserGroupOperateUserDTO;
import cn.neud.neusurvey.entity.statistics.StatisticChartEntity;
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
            if (memberEntityList.size() == 0){
                UserGroupEntity userGroupEntity = userGroupDao.selectById(ids[i]);
                System.out.println(userGroupEntity);
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
    public int deleteUserByPrimary(UserGroupOperateUserDTO dto) {
        String[] user_ids = dto.getUser_ids();
        for (int i = 0; i < user_ids.length; i++) {
            memberDao.softDeleteByPrimary(user_ids[i],dto.getGroup_id());
        }


        return 0;
    }

    @Override
    public int addGroupUser(UserGroupOperateUserDTO dto) {
        String[] user_ids = dto.getUser_ids();
        for (int i = 0; i < user_ids.length; i++) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setGroupId(dto.getGroup_id());
            memberEntity.setUserId(user_ids[i]);
            memberEntity.setCreator(dto.getCreator());
            memberEntity.setCreateDate(new Date(System.currentTimeMillis()));
            memberDao.insert(memberEntity);
//            memberDao.addGroupUser(user_ids[i],dto.getGroup_id());
        }

        return 0;
    }

    @Override
    public PageData<UserDTO> pageGroupUser(Map<String, Object> params) {
//        System.out.println("body"+params);
        Integer page = (Integer) params.get("page");
        Integer size = (Integer) params.get("size");
        //page从零开始
        params.put("page",page *size);
        List<UserDTO> list = userDao.pageGroupUser(params);
        String group_id = (String) params.get("group_id");
        String username = (String) params.get("username");
        Integer total = userDao.countGroupUser(group_id,username);
        PageData<UserDTO> pageData = new PageData<>(list,total);
        return pageData;
    }

    @Override
    public int countGroup(String id) {
        List<MemberEntity> memberEntityList = memberDao.selectByGroupId(id);
        String total = String.valueOf(memberEntityList.size());
        String online = "99";
        StatisticChartEntity heatmap = new StatisticChartEntity();
        StatisticChartEntity graphs = new StatisticChartEntity();


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
}