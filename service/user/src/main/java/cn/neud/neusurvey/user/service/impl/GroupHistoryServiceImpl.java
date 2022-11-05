package cn.neud.neusurvey.user.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.user.GroupHistoryEntity;
import cn.neud.neusurvey.entity.user.UserHistoryEntity;
import cn.neud.neusurvey.user.dao.UserHistoryDao;
import cn.neud.neusurvey.user.service.GroupHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.GroupHistoryDao;
import cn.neud.neusurvey.dto.user.GroupHistoryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;

/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class GroupHistoryServiceImpl extends CrudServiceImpl<GroupHistoryDao, GroupHistoryEntity, GroupHistoryDTO> implements GroupHistoryService {
    @Autowired
    private GroupHistoryDao groupHistoryDao;

    @Override
    public QueryWrapper<GroupHistoryEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<GroupHistoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public Result saveGroupHistory(GroupHistoryDTO dto) {
        Result result=new Result();

        GroupHistoryEntity groupHistoryEntity= groupHistoryDao.selectById(dto.getId());

        if(groupHistoryEntity!=null)
            return result.error("该id已存在");
        else groupHistoryEntity=new GroupHistoryEntity();

        BeanUtils.copyProperties(dto,groupHistoryEntity);

        System.out.println(groupHistoryEntity);
        groupHistoryEntity.setCreator(dto.getCreator());
        groupHistoryEntity.setCreateDate(new Date(System.currentTimeMillis()));
        groupHistoryEntity.setUpdater(dto.getCreator());
        groupHistoryEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        groupHistoryEntity.setIsDeleted("0");

        System.out.println(groupHistoryEntity);
        groupHistoryDao.insert(groupHistoryEntity);

        return result.ok("成功创建");

    }

    @Override
    public Result updateGroupHistory(GroupHistoryDTO dto) {
        Result result=new Result();

        GroupHistoryEntity groupHistoryEntity= groupHistoryDao.selectById(dto.getId());

        if(groupHistoryEntity==null)
            return result.error("该id不存在");

        BeanUtils.copyProperties(dto,groupHistoryEntity);
        groupHistoryEntity.setUpdateDate(new Date(System.currentTimeMillis()));

        groupHistoryDao.updateById(groupHistoryEntity);

        return result.ok(null);
    }

    @Override
    public Result deleteLogic(String[] ids) {
        Result result=new Result();
        boolean ifOK=true;
        String msg=new String();

        for(int i=0;i< ids.length;i++)
        {
            GroupHistoryEntity groupHistoryEntity=groupHistoryDao.selectById(ids[i]);

            if(groupHistoryEntity==null)
            {
                ifOK&=false;
                msg+="未找到id为"+ids[i]+"的用户实体\n";
                continue;
            }

            groupHistoryEntity.setIsDeleted("1");
            groupHistoryDao.updateById(groupHistoryEntity);

        }

        if(ifOK) return result.ok(null);
        else return result.error(msg);
    }
}