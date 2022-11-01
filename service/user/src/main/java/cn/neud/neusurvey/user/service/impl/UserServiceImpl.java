package cn.neud.neusurvey.user.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserLoginDTO;
import cn.neud.neusurvey.entity.user.UserEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserDao, UserEntity, UserDTO> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public QueryWrapper<UserEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public Result loginValidate(UserLoginDTO userLoginDTO) {

        Result result = new Result();
        UserEntity user = userDao.selectByUsername(userLoginDTO.getUsername());

        boolean ifHaveUser = user != null;
        if (ifHaveUser) {
            boolean ifPasswordCorrect = user.getPassword().equals(userLoginDTO.getPassword());

            if (ifPasswordCorrect) {
                result.ok(null);
            } else {
                result.error("密码错误");
            }
        } else {
            result.error("找不到用户");
        }

        return result;
    }
}