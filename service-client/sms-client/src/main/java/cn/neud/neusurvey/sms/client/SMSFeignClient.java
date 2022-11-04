package cn.neud.neusurvey.sms.client;

import cn.neud.common.annotation.LogOperation;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserEmailLoginDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "neusurvey-sms",name = "neusurvey-sms")
@Repository
public interface SMSFeignClient {

    @GetMapping("getCode")
    @ApiOperation("邮箱获取验证码")
    @LogOperation("邮箱获取验证码")
    @RequiresPermissions("user:user:login")
    public Result loginByEmail(@RequestBody UserEmailLoginDTO userEmailLoginDTO);
}
