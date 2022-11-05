package cn.neud.neusurvey.sms.controller;

import cn.neud.common.annotation.LogOperation;
import cn.neud.common.utils.Result;
import cn.neud.common.validator.ValidatorUtils;
import cn.neud.common.validator.group.AddGroup;
import cn.neud.common.validator.group.DefaultGroup;
import cn.neud.neusurvey.dto.user.UserEmailLoginDTO;
import cn.neud.neusurvey.sms.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("sms/mail")
@Api(tags = "sms")
public class MailController {

    @Resource
    private MailService mailService;

    @GetMapping("code")
    @ApiOperation("邮箱获取验证码")
    @LogOperation("邮箱获取验证码")
    @RequiresPermissions("user:user:login")
    public Result loginByEmail(@RequestBody UserEmailLoginDTO userEmailLoginDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userEmailLoginDTO, AddGroup.class, DefaultGroup.class);
        return mailService.emailLoginValidate(userEmailLoginDTO);
    }

}
