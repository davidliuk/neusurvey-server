package cn.neud.neusurvey.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.entity.user.UserHistoryEntity;
import cn.neud.neusurvey.user.dao.UserHistoryDao;
import cn.neud.neusurvey.user.service.HttpUtils;
import cn.neud.neusurvey.user.service.UserHistoryService;
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
import java.util.Date;
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

    @Resource
    private UserHistoryDao userHistoryDao;

    @Override
    public QueryWrapper<UserEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String role = (String) params.get("role");
        String username = (String) params.get("username");
        String nickname = (String) params.get("nickname");
        String isDeleted = (String) params.get("isDeleted");

        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(role), "role", role);
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.like(StringUtils.isNotBlank(nickname), "nickname", nickname);

        if(isDeleted!=null&&isDeleted.equals("1"))
            wrapper.eq(StringUtils.isNotBlank(isDeleted),"is_deleted", isDeleted);
        if(isDeleted!=null&&isDeleted.equals("0"))
            wrapper.ne("is_deleted", "1");


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
                result.error("????????????");
            }
        } else {
            result.error("???????????????");
        }

        return result;
    }

    @Override
    public Result register(UserRegisterDTO userRegisterDTO) {

        Result result = new Result();

        //????????????????????????
        UserEntity user = userDao.selectByUsername(userRegisterDTO.getUsername());

        if (user != null) {
            if (user.getMobile().equals(userRegisterDTO.getMobile()))
                return result.error("?????????????????????");
            return result.error("??????????????????");
        }

        UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userRegisterDTO, userEntity);

            String userId = UuidUtils.generateUuid();
            userEntity.setId(userId);
            userEntity.setCreator(userId);
//            userEntity.setUpdater(userId);
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
                msg += "?????????id???" + ids[i] + "???????????????\n";
                continue;
            }


            //????????????
            UserHistoryEntity historyEntity=new UserHistoryEntity();
            BeanUtils.copyProperties(userEntity, historyEntity);
            historyEntity.setUserId(userEntity.getId());
            historyEntity.setId(UuidUtils.generateUuid());
            historyEntity.setUpdateDate(new java.util.Date(System.currentTimeMillis()));
            userHistoryDao.insert(historyEntity);


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
            return result.error("?????????id???" + dto.getId() + "???????????????");

        UserEntity userEntity_username = userDao.selectByUsername(dto.getUsername());

        if (userEntity_username != null && !dto.getUsername().equals(userEntity.getUsername()))
            return result.error("?????????????????????" + dto.getUsername() + "???????????????");

        //????????????
        UserHistoryEntity historyEntity=new UserHistoryEntity();
        BeanUtils.copyProperties(userEntity, historyEntity);
        historyEntity.setUserId(userEntity.getId());
        historyEntity.setId(UuidUtils.generateUuid());
        historyEntity.setUpdateDate(new java.util.Date(System.currentTimeMillis()));
        userHistoryDao.insert(historyEntity);

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
        result.setMsg("??????????????????????????????????????????????????????");
        return result;
    }

    @Override
    public Result recoverUser(String[] ids) {

        Result result=new Result();

        String msg=new String();

        boolean ifSuccess=true;

        //?????????id???????????????????????????????????????????????????????????????
        for(int i=0;i< ids.length;i++)
        {
            //??????????????????
            UserHistoryEntity userHistoryEntity=userHistoryDao.selectById(ids[i]);

            if(userHistoryEntity==null)
            {
                msg+="????????????id???"+ids[i]+"?????????\n";
                ifSuccess = false;
                continue;
            }

            if(userHistoryEntity.getIsDeleted()!=null
                    && userHistoryEntity.getIsDeleted().equals("1"))
            {
                msg+="id???"+ids[i]+"?????????????????????\n";
                ifSuccess = false;
                continue;
            }

            //??????????????????
            UserEntity userEntity=userDao.selectById(userHistoryEntity.getUserId());
//            if(userEntity!=null)
//            {
//                UserHistoryEntity userHistoryEntity_new=new UserHistoryEntity();
//                BeanUtils.copyProperties(userEntity,userHistoryEntity_new);
//                userHistoryEntity_new.setId(UuidUtils.generateUuid());
//                userHistoryEntity_new.setUserId(userEntity.getId());
//                userHistoryEntity_new.setUpdateDate(new Date(System.currentTimeMillis()));
//                userHistoryDao.insert(userHistoryEntity_new);
//            }

            //??????????????????
            BeanUtils.copyProperties(userHistoryEntity,userEntity);
            userEntity.setId(userHistoryEntity.getUserId());
            userEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            userEntity.setIsDeleted("0");
            if(userEntity==null)
                userDao.insert(userEntity);
            else
                userDao.updateById(userEntity);
        }

        if(ifSuccess) return result.ok(null);
        else return result.error(msg);
    }

    //??????id
    @Override
    public Result recoverFromDelete(String[] ids) {

        Result result=new Result();

        String msg=new String();

        boolean ifSuccess=true;

        //?????????id???????????????????????????????????????????????????????????????
        for(int i=0;i< ids.length;i++)
        {
            //???????????????????????????????????????
            //select * from user_history where update_date= (select max(update_date) from user_history where user_id=#{userId});
            //UserHistoryEntity userHistoryEntity=userHistoryDao.selectTheLatest(ids[i]);

            UserEntity userEntity=userDao.selectById(ids[i]);
            if(userEntity==null)
            {
                ifSuccess=false;
                msg+="????????????id???"+ids[i]+"?????????\n";
                continue;
            }
            userEntity.setIsDeleted("0");
            userDao.deleteById(ids[i]);
            userDao.insert(userEntity);
        }

        if(ifSuccess) return result.ok(null);
        else return result.error(msg);
    }

    @Override
    public Result codeLoginValidate(UserVerificationLoginDTO userVerificationLoginDTO) {

        Result result = new Result();

        String inputVerifationCode = userVerificationLoginDTO.getVerificationCode();
        String inputPhone = userVerificationLoginDTO.getPhone();

        // ?????????????????????????????????????????????
        UserEntity user = userDao.selectByMobile(inputPhone);

        boolean ifHaveUser = user != null;

        if (ifHaveUser == false) {
            result.error("??????????????????");
        } else {
            // ????????????????????????????????????
            boolean ifVerificationCorrect;
            System.out.println("?????????" + inputVerifationCode);
            System.out.println("?????????" + Code);
            if (inputVerifationCode.equals(Code)) {
                ifVerificationCorrect = true;
            } else {
                ifVerificationCorrect = false;
            }

            if (ifVerificationCorrect) {
                result.ok("???????????????");

            } else {
                result.error("???????????????");
            }
        }

        return result;
    }

    @Override
    public Result sendCode(SendCodeDTO sendCodeDTO) {

        Result result = new Result();

        String inputPhone = sendCodeDTO.getPhone();

        // ?????????????????????????????????????????????
        UserEntity user = userDao.selectByMobile(inputPhone);
        boolean ifHaveUser = user != null;

        if (ifHaveUser == false) {
            result.error("??????????????????");
        } else {
            // ??????????????????????????????
            send(inputPhone);
            boolean ifSendCodeCorrect = true;
            if (ifSendCodeCorrect) {
                result.ok("?????????????????????");
                result.setData(Code);

            } else {
                result.error("?????????????????????");
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
        //?????????header????????????(?????????????????????)???Authorization:APPCODE 83359fd73fe94948385f570e3c139105
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
             * ??????????????????:
             * HttpUtils??????
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * ??????
             *
             * ????????????????????????
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //??????response???body
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
        //?????????header????????????(?????????????????????)???Authorization:APPCODE 83359fd73fe94948385f570e3c139105
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
             * ??????????????????:
             * HttpUtils??????
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * ??????
             *
             * ????????????????????????
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //??????response???body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}