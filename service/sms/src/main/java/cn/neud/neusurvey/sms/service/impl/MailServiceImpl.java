package cn.neud.neusurvey.sms.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserEmailLoginDTO;
import cn.neud.neusurvey.sms.service.MailService;
import cn.neud.neusurvey.sms.utils.MailUtils;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

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
