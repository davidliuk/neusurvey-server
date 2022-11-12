package cn.neud.neusurvey.sms.client;

import cn.neud.common.annotation.LogOperation;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserEmailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "neusurvey-sms", name = "neusurvey-sms")
//@RequestMapping("/sms/mail")
@Repository
public interface SMSFeignClient {

    @GetMapping("code")
    @ApiOperation("邮箱获取验证码")
    @LogOperation("邮箱获取验证码")
    public Result loginByEmail(@RequestBody UserEmailDTO userEmailDTO);

}
