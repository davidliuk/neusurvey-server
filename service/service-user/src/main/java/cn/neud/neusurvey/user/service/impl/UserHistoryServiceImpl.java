package cn.neud.neusurvey.user.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserHistoryUpdateDTO;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.entity.user.UserHistoryEntity;
import cn.neud.neusurvey.user.service.UserHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.UserHistoryDao;
import cn.neud.neusurvey.dto.user.UserHistoryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;

/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class UserHistoryServiceImpl extends CrudServiceImpl<UserHistoryDao, UserHistoryEntity, UserHistoryDTO> implements UserHistoryService {

    @Autowired
    private UserHistoryDao userHistoryDao;

    @Override
    public QueryWrapper<UserHistoryEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String userId = (String)params.get("userId");

        QueryWrapper<UserHistoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq( "user_id", userId);

        return wrapper;
    }


    @Override
    public Result saveUserHistory(UserHistoryDTO dto) {
        Result result=new Result();

        UserHistoryEntity userHistoryEntity= userHistoryDao.selectById(dto.getId());

        if(userHistoryEntity!=null)
            return result.error("该id已存在");
        else userHistoryEntity=new UserHistoryEntity();

        BeanUtils.copyProperties(dto,userHistoryEntity);

        userHistoryEntity.setCreator(dto.getCreator());
        userHistoryEntity.setCreateDate(new Date(System.currentTimeMillis()));
        userHistoryEntity.setUpdater(dto.getCreator());
        userHistoryEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        userHistoryEntity.setIsDeleted("0");

        userHistoryDao.insert(userHistoryEntity);

        return result.ok(null);

    }

    @Override
    public Result updateUserHistory(UserHistoryUpdateDTO dto) {
        Result result=new Result();

        UserHistoryEntity userHistoryEntity= userHistoryDao.selectById(dto.getId());

        if(userHistoryEntity==null)
            return result.error("该id不存在");

        BeanUtils.copyProperties(dto,userHistoryEntity);
        userHistoryEntity.setUpdateDate(new java.util.Date(System.currentTimeMillis()));

        userHistoryDao.updateById(userHistoryEntity);

        return result.ok(null);

    }

    @Override
    public Result deleteLogic(String[] ids) {

        Result result=new Result();
        boolean ifOK=true;
        String msg=new String();

        for(int i=0;i< ids.length;i++)
        {
            UserHistoryEntity userHistoryEntity=userHistoryDao.selectById(ids[i]);

            if(userHistoryEntity==null)
            {
                ifOK&=false;
                msg+="未找到id为"+ids[i]+"的用户实体\n";
                continue;
            }

            userHistoryEntity.setIsDeleted("1");
            userHistoryDao.updateById(userHistoryEntity);

        }

        if(ifOK) return result.ok(null);
        else return result.error(msg);
    }
}