package cn.neud.neusurvey.sms.service;



import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserEmailLoginDTO;

public interface CodeService {

    Result emailLoginValidate(UserEmailLoginDTO userEmailLoginDTO);


}
