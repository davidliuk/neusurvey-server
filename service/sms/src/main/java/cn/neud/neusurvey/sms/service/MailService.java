package cn.neud.neusurvey.sms.service;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserEmailDTO;

public interface MailService {

    Result emailLoginValidate(UserEmailDTO userEmailDTO);

}
