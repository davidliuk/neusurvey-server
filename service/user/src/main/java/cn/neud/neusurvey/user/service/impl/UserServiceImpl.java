package cn.neud.neusurvey.user.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserEmailLoginDTO;
import cn.neud.neusurvey.dto.user.UserLoginDTO;
import cn.neud.neusurvey.dto.user.UserRegisterDTO;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.user.utils.MailUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Calendar;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

/**
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserDao, UserEntity, UserDTO> implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public QueryWrapper<UserEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String role = (String)params.get("role");
        String username = (String)params.get("username");
        String nickname = (String)params.get("nickname");

        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(role), "role", role);
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.like(StringUtils.isNotBlank(nickname), "nickname", nickname);

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

    @Override
    public Result register(UserRegisterDTO userRegisterDTO) {

        Result result=new Result();

        //用户名重复性检查
        UserEntity user=userDao.selectByUsername(userRegisterDTO.getUsername());
        if(user!=null)
        {
            result.error("该用户已存在");
        }
        else
        {
            UserEntity userEntity=new UserEntity();
            BeanUtils.copyProperties(userRegisterDTO,userEntity);

            String userId= UuidUtils.generateUuid();
            userEntity.setId(userId);
            userEntity.setCreator(userId);
            userEntity.setUpdater(userId);
            userEntity.setCreateDate(new Date(System.currentTimeMillis()));
            userEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            userEntity.setIsDeleted(String.valueOf(0));

            userDao.insert(userEntity);
            result.ok(null);
        }
        return result;
    }

    @Override
    public Result deleteLogic(String[] ids) {

        Result result=new Result();
        boolean ifOK=true;
        String msg=new String();

        for(int i=0;i< ids.length;i++)
        {
            UserEntity userEntity=userDao.selectById(ids[i]);

            if(userEntity==null)
            {
                ifOK&=false;
                msg+="未找到id为"+ids[i]+"的用户实体\n";
                continue;
            }

            userEntity.setIsDeleted("1");
            userDao.updateById(userEntity);

        }

        if(ifOK) return result.ok(null);
        else return result.error(msg);
    }

    @Override
    public Result updateUser(UserDTO dto) {

        Result result = new Result();


        UserEntity userEntity = userDao.selectById(dto.getId());

        if (userEntity == null)
            return result.error("未找到id为" + dto.getId() + "的用户实体");

        UserEntity userEntity_username= userDao.selectByUsername(dto.getUsername());

        if (userEntity_username != null && !dto.getUsername().equals(userEntity.getUsername()))
            return result.error("已存在用户名为"+dto.getUsername()+"的用户实体");

        BeanUtils.copyProperties(dto,userEntity);

        userDao.updateById(userEntity);

        return result.ok(null);

    }



    public Result emailLoginValidate(UserEmailLoginDTO userEmailLoginDTO) {
//       UserEntity user = userDao.selectByEmail(userEmailLoginDTO.getEmail());
        Result result = new Result();
        String verifyCode = MailUtils.sendMail(userEmailLoginDTO.getEmail());
        result.setData(verifyCode);
        result.setMsg("验证码已发送至指定邮箱，请注意查收！");
        return result;
    }

    @Override
    public Result emailLoginValidate(UserEmailLoginDTO userEmailLoginDTO) {
//       UserEntity user = userDao.selectByEmail(userEmailLoginDTO.getEmail());
        Result result = new Result();
        String verifyCode = MailUtils.sendMail(userEmailLoginDTO.getEmail());
        result.setData(verifyCode);
        result.setMsg("验证码已发送至指定邮箱，请注意查收！");
        return result;
    }
}