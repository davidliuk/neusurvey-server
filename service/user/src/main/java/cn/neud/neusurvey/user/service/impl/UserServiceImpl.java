package cn.neud.neusurvey.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.user.service.HttpUtils;
import cn.neud.neusurvey.user.utils.MailUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.quartz.Calendar;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    public String Code;

    @Override
    public QueryWrapper<UserEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String role = (String) params.get("role");
        String username = (String) params.get("username");
        String nickname = (String) params.get("nickname");

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
                StpUtil.login(user.getId());
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

        Result result = new Result();

        //用户名重复性检查
        UserEntity user = userDao.selectByUsername(userRegisterDTO.getUsername());

        if (user != null)
            return result.error("该用户已存在");

        if (user.getMobile().equals(userRegisterDTO.getMobile()))
            return result.error("该手机号已存在");

        UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userRegisterDTO, userEntity);

            String userId = UuidUtils.generateUuid();
            userEntity.setId(userId);
            userEntity.setCreator(userId);
            userEntity.setUpdater(userId);
            userEntity.setCreateDate(new Date(System.currentTimeMillis()));
            userEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            userEntity.setIsDeleted(String.valueOf(0));

        userDao.insert(userEntity);

        return result.ok(null);

    }

    @Override
    public Result deleteLogic(String[] ids) {

        Result result = new Result();
        boolean ifOK = true;
        String msg = new String();

        for (int i = 0; i < ids.length; i++) {
            UserEntity userEntity = userDao.selectById(ids[i]);

            if (userEntity == null) {
                ifOK &= false;
                msg += "未找到id为" + ids[i] + "的用户实体\n";
                continue;
            }

            userEntity.setIsDeleted("1");
            userDao.updateById(userEntity);

        }

        if (ifOK) return result.ok(null);
        else return result.error(msg);
    }

    @Override
    public Result updateUser(UserDTO dto) {

        Result result = new Result();


        UserEntity userEntity = userDao.selectById(dto.getId());

        if (userEntity == null)
            return result.error("未找到id为" + dto.getId() + "的用户实体");

        UserEntity userEntity_username = userDao.selectByUsername(dto.getUsername());

        if (userEntity_username != null && !dto.getUsername().equals(userEntity.getUsername()))
            return result.error("已存在用户名为" + dto.getUsername() + "的用户实体");

        BeanUtils.copyProperties(dto, userEntity);
        userEntity.setUpdateDate(new java.util.Date(System.currentTimeMillis()));

        userDao.updateById(userEntity);

        return result.ok(null);

    }

    @Override
    public Result emailLoginValidate(UserEmailDTO userEmailDTO) {
//       UserEntity user = userDao.selectByEmail(userEmailLoginDTO.getEmail());
        Result result = new Result();
        String verifyCode = MailUtils.sendMail(userEmailDTO.getEmail());
        result.setData(verifyCode);
        result.setMsg("验证码已发送至指定邮箱，请注意查收！");
        return result;
    }

    @Override
    public Result codeLoginValidate(UserVerificationLoginDTO userVerificationLoginDTO) {

        Result result = new Result();

        String inputVerifationCode = userVerificationLoginDTO.getVerificationCode();
        String inputPhone = userVerificationLoginDTO.getPhone();

        // 判断数据库中有无此手机号的用户
        UserEntity user = userDao.selectByMobile(inputPhone);

        boolean ifHaveUser = user != null;

        if (ifHaveUser == false) {
            result.error("此号码未注册");
        } else {
            // 判断输入的验证码是否正确
            boolean ifVerificationCorrect;
            System.out.println("给我的" + inputVerifationCode);
            System.out.println("拥有的" + Code);
            if (inputVerifationCode.equals(Code)) {
                ifVerificationCorrect = true;
            } else {
                ifVerificationCorrect = false;
            }

            if (ifVerificationCorrect) {
                result.ok("验证码正确");

            } else {
                result.error("验证码错误");
            }

        }

        return result;
    }

    @Override
    public Result sendCode(SendCodeDTO sendCodeDTO) {

        Result result = new Result();

        String inputPhone = sendCodeDTO.getPhone();

        // 判断数据库中有无此手机号的用户
        UserEntity user = userDao.selectByMobile(inputPhone);
        boolean ifHaveUser = user != null;

        if (ifHaveUser == false) {
            result.error("此号码未注册");
        } else {
            // 根据手机号发送验证码
            send(inputPhone);
            boolean ifSendCodeCorrect = true;
            if (ifSendCodeCorrect) {
                result.ok("验证码发送成功");
                result.setData(Code);

            } else {
                result.error("验证码发送失败");
            }
        }
        return result;
    }

    public void send(String mobile) {

//        Random r = new Random( System.currentTimeMillis() );
//
//        int a = 10000 + r.nextInt(20000);
//        String Code = String.valueOf(a);
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "e65d4b950419443784c67224aebc4c14";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        Random r = new Random(System.currentTimeMillis());
        int a = 10000 + r.nextInt(20000);
        Code = String.valueOf(a);
        querys.put("param", "**code**:" + Code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "e65d4b950419443784c67224aebc4c14";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "18866106307");
        Random r = new Random(System.currentTimeMillis());
        int a = 10000 + r.nextInt(20000);
        String Code = String.valueOf(a);
        querys.put("param", "**code**:" + Code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}