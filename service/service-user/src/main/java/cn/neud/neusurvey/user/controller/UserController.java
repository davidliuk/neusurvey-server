package cn.neud.neusurvey.user.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.neud.common.annotation.LogOperation;
import cn.neud.common.constant.Constant;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.ExcelUtils;
import cn.neud.common.utils.Result;
import cn.neud.common.validator.AssertUtils;
import cn.neud.common.validator.ValidatorUtils;
import cn.neud.common.validator.group.AddGroup;
import cn.neud.common.validator.group.DefaultGroup;
import cn.neud.common.validator.group.UpdateGroup;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.mapper.user.UserMapper;
import cn.neud.neusurvey.sms.client.SMSFeignClient;
import cn.neud.neusurvey.excel.user.UserExcel;
import cn.neud.neusurvey.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("user/user")
@Api(tags = "user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private SMSFeignClient smsFeignClient;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("user:user:page")
    public Result<PageData<UserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<UserDTO> page = userService.page(params);

        return new Result<PageData<UserDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("user:user:info")
    public Result<UserDTO> get(@PathVariable("id") String id) {
        UserDTO data = userService.get(id);
        Result<UserDTO> result = new Result<UserDTO>().ok(data);
        result.setMsg("10086");
        return result;
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("user:user:save")
    public Result save(@RequestBody UserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userService.save(dto);

        return new Result();
    }

    @PostMapping("login")
    @ApiOperation("登录")
    @LogOperation("登录")
    @RequiresPermissions("user:user:login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userLoginDTO, AddGroup.class, DefaultGroup.class);
        return userService.loginValidate(userLoginDTO);
    }

    @PostMapping("loginByEmail")
    @ApiOperation("邮箱登录")
    @LogOperation("邮箱登录")
    @RequiresPermissions("user:user:login")
    public Result loginByEmail(@RequestBody UserEmailDTO userEmailDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userEmailDTO, AddGroup.class, DefaultGroup.class);
        return smsFeignClient.loginByEmail(userEmailDTO);
    }

    @PostMapping("register")
    @ApiOperation("注册")
    @LogOperation("注册")
    @RequiresPermissions("user:user:register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userRegisterDTO, AddGroup.class, DefaultGroup.class);
        return userService.register(userRegisterDTO);
    }
//  雷世鹏:这个只有指定正确的id才能完成操作,没有相应提示
//    @PutMapping
//    @ApiOperation("修改")
//    @LogOperation("修改")
//    @RequiresPermissions("user:user:update")
//    public Result update(@RequestBody UserDTO dto){
//        //效验数据
//        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
//
//        userService.update(dto);
//
//        return new Result();
//    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("user:user:update")
    public Result update(@RequestBody UserDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        return userService.updateUser(dto);

    }

    @PostMapping("recover")
    @ApiOperation("恢复用户")
    @LogOperation("恢复用户")
    @RequiresPermissions("user:user:recover")
    public Result recover(@RequestBody String[] ids) {
        //效验数据
        ValidatorUtils.validateEntity(ids, UpdateGroup.class, DefaultGroup.class);

        return userService.recoverUser(ids);

    }


    @PostMapping("recoverFromDelete")
    @ApiOperation("恢复删除用户")
    @LogOperation("恢复删除用户")
    @RequiresPermissions("user:user:recoverFromDelete")
    public Result recoverFromDelete(@RequestBody String[] ids) {
        //效验数据
        ValidatorUtils.validateEntity(ids, UpdateGroup.class, DefaultGroup.class);

        return userService.recoverFromDelete(ids);

    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:user:delete")
    public Result deleteReal(@RequestBody String[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        userService.delete(ids);

        return new Result();
    }

    /*
    雷世鹏: 逻辑删除
     */
    @DeleteMapping("logic")
    @ApiOperation("逻辑删除")
    @LogOperation("逻辑删除")
    @RequiresPermissions("user:user:deleteLogic")
    public Result deleteLogic(@RequestBody String[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        return userService.deleteLogic(ids);

    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("user:user:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserDTO> list = userService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, UserExcel.class);
    }

    @PostMapping("import/user")
    @ApiOperation("导入")
    @LogOperation("导入")
//    @RequiresPermissions("questionnaire:teacher:save")
    public Result importUser(@RequestBody MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        List<UserExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), UserExcel.class, importParams);
        for (UserExcel userExcel : list) {
            UserEntity user = UserMapper.INSTANCE.fromExcel(userExcel);
//            UserEntity user = new UserEntity();
//            BeanUtils.copyProperties(user, userExcel);
            System.out.println(userExcel);
            System.out.println(user);
            user.setId(null);
            user.setRole(2);
            userService.insert(user);
        }
        return new Result();
    }

    @PostMapping("import/respondent")
    @ApiOperation("导入")
    @LogOperation("导入")
//    @RequiresPermissions("questionnaire:teacher:save")
    public Result importRespondent(@RequestBody MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        List<UserExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), UserExcel.class, importParams);
        for (UserExcel userExcel : list) {
            UserEntity user = new UserEntity();
            BeanUtils.copyProperties(user, userExcel);
            System.out.println(userExcel);
            System.out.println(user);
            user.setId(null);
            user.setRole(3);
            userService.insert(user);
        }
        return new Result();
    }

    @PostMapping("verificationLogin")
    @ApiOperation("验证码登录")
    @LogOperation("验证码登录")
    @RequiresPermissions("user:user:verificationLogin")
    public Result verificationLogin(@RequestBody UserVerificationLoginDTO userVerificationLoginDTO) {
        //效验数据
        ValidatorUtils.validateEntity(userVerificationLoginDTO, AddGroup.class, DefaultGroup.class);
        return userService.codeLoginValidate(userVerificationLoginDTO);
    }

    @PostMapping("sendCode")
    @ApiOperation("发送验证码")
    @LogOperation("发送验证码")
    @RequiresPermissions("user:user:sendCode")
    public Result sendCode(@RequestBody SendCodeDTO sendCodeDTO) {
        //效验数据
        ValidatorUtils.validateEntity(sendCodeDTO, AddGroup.class, DefaultGroup.class);
        return userService.sendCode(sendCodeDTO);
    }

}
