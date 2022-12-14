package cn.neud.neusurvey.user.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.SendCodeDTO;
import cn.neud.neusurvey.dto.user.UserPasswordResetDTO;
import cn.neud.neusurvey.dto.user.UserVerificationLoginDTO;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.hash.Hash;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.user.dao.SecretQuestionDao;
import cn.neud.neusurvey.entity.user.SecretQuestionEntity;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.user.service.HttpUtils;
import cn.neud.neusurvey.user.service.SecretQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class SecretQuestionServiceImpl extends CrudServiceImpl<SecretQuestionDao, SecretQuestionEntity, SecretQuestionDTO> implements SecretQuestionService {
    @Resource
    private UserDao userDao;
    public String Code;

    @Override
    public QueryWrapper<SecretQuestionEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");
        String userId = (String) params.get("userId");

        QueryWrapper<SecretQuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        wrapper.eq(StringUtils.isNotBlank(userId), "user_id", userId);

        return wrapper;
    }

    @Override
    public List<SecretQuestionDTO> list(String username) {
        UserEntity user = userDao.selectByUsername(username);
        System.out.println(user);
        System.out.println(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        return super.list(map);
    }

//    @Override
//    public Result retrieve(UserVerificationLoginDTO userVerificationLoginDTO) {
//
//        Result result = new Result();
//
//        String inputVerifationCode = userVerificationLoginDTO.getVerificationCode();
//        String inputPhone = userVerificationLoginDTO.getPhone();
//
//        // ?????????????????????????????????????????????
//        UserEntity user = userDao.selectByMobile(inputPhone);
//
//        boolean ifHaveUser = user != null;
//
//        if (ifHaveUser == false) {
//            result.error("??????????????????");
//        } else {
//            // ????????????????????????????????????
//            boolean ifVerificationCorrect;
//            System.out.println("?????????" + inputVerifationCode);
//            System.out.println("?????????" + Code);
//            if (inputVerifationCode.equals(Code)) {
//                ifVerificationCorrect = true;
//            } else {
//                ifVerificationCorrect = false;
//            }
//
//            if (ifVerificationCorrect) {
//                result.ok("???????????????");
//                String password = user.getPassword();
//                result.setData(password);
//            } else {
//                result.error("???????????????");
//            }
//
//        }
//
//        return result;
//    }

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

//    public void send(String mobile) {
//
//        String host = "https://gyytz.market.alicloudapi.com";
//        String path = "/sms/smsSend";
//        String method = "POST";
//        String appcode = "e65d4b950419443784c67224aebc4c14";
//        Map<String, String> headers = new HashMap<String, String>();
//        //?????????header????????????(?????????????????????)???Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("mobile", mobile);
//        Random r = new Random(System.currentTimeMillis());
//        int a = 10000 + r.nextInt(20000);
//        Code = String.valueOf(a);
//        querys.put("param", "**code**:" + Code + ",**minute**:5");
//        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
//        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
//        Map<String, String> bodys = new HashMap<String, String>();
//
//
//        try {
//            /**
//             * ??????????????????:
//             * HttpUtils??????
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//             * ??????
//             *
//             * ????????????????????????
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//             */
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
//            //??????response???body
//            //System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public Result reset(UserPasswordResetDTO userPasswordResetDTO) {

        Result result = new Result();

        String inputVerifationCode = userPasswordResetDTO.getVerificationCode();
        String inputPhone = userPasswordResetDTO.getPhone();
        String inputPassword = userPasswordResetDTO.getPassword();

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
                userDao.reset(inputPhone, inputPassword);
            } else {
                result.error("???????????????");
            }

        }

        return result;
    }

    @Override
    public boolean saveSecret(SecretDTO dto) {
        List<SecretQuestionDTO> list = list(dto.getUsername());
        System.out.println(list);
        Map<String, SecretQuestionDTO> map = new HashMap<>();
        for (SecretQuestionDTO questionDTO : list) {
            map.put(questionDTO.getId(), questionDTO);
        }
        for (int i = 0; i < dto.getAnswers().size(); i++) {
            Answer answer = dto.getAnswers().get(i);
            if (!answer.getAnswer().equals(map.get(answer.getId()).getAnswer())) {
                return false;
            }
        }
        UserEntity user = userDao.selectByUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", dto.getUsername());
//        params.put("password", dto.getPassword());
        userDao.updateById(user);
        System.out.println(user);
        return true;
    }

    public Result retrieve(UserVerificationLoginDTO userVerificationLoginDTO) {

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
                String password = user.getPassword();
                result.setData(password);
            } else {
                result.error("???????????????");
            }

        }

        return result;
    }

    @Override
    public void add(String username, SecretChangeDTO[] dtos) {
//        String userId = StpUtil.getLoginIdAsString();
        UserEntity user = userDao.selectByUsername(username);
        System.out.println(user);
        System.out.println(user.getId());
        String userId = user.getId();
        for (SecretChangeDTO dto : dtos) {
            SecretQuestionDTO questionDTO = new SecretQuestionDTO();
            questionDTO.setUserId(userId);
            questionDTO.setAnswer(dto.getAnswer());
            questionDTO.setStem(dto.getStem());
            System.out.println(questionDTO);
            this.save(questionDTO);
        }
    }

    @Override
    public void update(String username, SecretChangeDTO[] dtos) {
        Map<String, Object> params = new HashMap<>();
//        String userId = StpUtil.getLoginIdAsString();
        UserEntity user = userDao.selectByUsername(username);
        System.out.println(user);
        System.out.println(user.getId());
        String userId = user.getId();
        params.put("userId", userId);
        this.delete(params);
        this.add(username, dtos);
    }

//    @Override
//    public Result sendCode(SendCodeDTO sendCodeDTO) {
//
//        Result result = new Result();
//
//        String inputPhone = sendCodeDTO.getPhone();
//
//        // ?????????????????????????????????????????????
//        UserEntity user = userDao.selectByMobile(inputPhone);
//        boolean ifHaveUser = user != null;
//
//        if (ifHaveUser == false) {
//            result.error("??????????????????");
//        } else {
//            // ??????????????????????????????
//            send(inputPhone);
//            boolean ifSendCodeCorrect = true;
//            if (ifSendCodeCorrect) {
//                result.ok("?????????????????????");
//                result.setData(Code);
//
//            } else {
//                result.error("?????????????????????");
//            }
//        }
//        return result;
//    }

    public void send(String mobile) {

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
}
