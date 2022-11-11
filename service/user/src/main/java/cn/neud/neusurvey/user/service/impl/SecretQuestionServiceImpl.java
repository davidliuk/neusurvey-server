package cn.neud.neusurvey.user.service.impl;

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
    public QueryWrapper<SecretQuestionEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        String userId = (String)params.get("userId");

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
                String password = user.getPassword();
                result.setData(password);
            } else {
                result.error("验证码错误");
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
        for (SecretChangeDTO dto: dtos) {
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
}
