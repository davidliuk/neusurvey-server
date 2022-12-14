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
import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.dto.user.*;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.mapper.user.UserMapper;
import cn.neud.neusurvey.sms.client.SMSFeignClient;
import cn.neud.neusurvey.excel.user.UserExcel;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import cn.neud.neusurvey.user.service.MemberService;
import cn.neud.neusurvey.user.service.UserGroupService;
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
import java.util.Date;
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

    @Resource
    private SurveyFeignClient surveyFeignClient;

    @Resource
    private MemberService memberService;

    @GetMapping("page")
    @ApiOperation("??????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "??????????????????1??????", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "?????????????????????", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "????????????", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "????????????????????????(asc???desc)", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("user:user:page")
    public Result<PageData<UserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<UserDTO> page = userService.page(params);

        return new Result<PageData<UserDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("??????")
    @RequiresPermissions("user:user:info")
    public Result<UserDTO> get(@PathVariable("id") String id) {
        UserDTO data = userService.get(id);
        Result<UserDTO> result = new Result<UserDTO>().ok(data);
        result.setMsg("10086");
        return result;
    }

    @PostMapping
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("user:user:save")
    public Result save(@RequestBody UserDTO dto) {
        //????????????
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userService.save(dto);

        return new Result();
    }

    @PostMapping("login")
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("user:user:login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        //????????????
        ValidatorUtils.validateEntity(userLoginDTO, AddGroup.class, DefaultGroup.class);
        return userService.loginValidate(userLoginDTO);
    }

    @PostMapping("loginByEmail")
    @ApiOperation("????????????")
    @LogOperation("????????????")
    @RequiresPermissions("user:user:login")
    public Result loginByEmail(@RequestBody UserEmailDTO userEmailDTO) {
        //????????????
        ValidatorUtils.validateEntity(userEmailDTO, AddGroup.class, DefaultGroup.class);
        return smsFeignClient.loginByEmail(userEmailDTO);
    }

    @PostMapping("register")
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("user:user:register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        //????????????
        ValidatorUtils.validateEntity(userRegisterDTO, AddGroup.class, DefaultGroup.class);
        return userService.register(userRegisterDTO);
    }
//  ?????????:???????????????????????????id??????????????????,??????????????????
//    @PutMapping
//    @ApiOperation("??????")
//    @LogOperation("??????")
//    @RequiresPermissions("user:user:update")
//    public Result update(@RequestBody UserDTO dto){
//        //????????????
//        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
//
//        userService.update(dto);
//
//        return new Result();
//    }

    @PutMapping
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("user:user:update")
    public Result update(@RequestBody UserDTO dto) {
        //????????????
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        return userService.updateUser(dto);

    }

    @PostMapping("recover")
    @ApiOperation("????????????")
    @LogOperation("????????????")
    @RequiresPermissions("user:user:recover")
    public Result recover(@RequestBody String[] ids) {
        //????????????
        ValidatorUtils.validateEntity(ids, UpdateGroup.class, DefaultGroup.class);

        return userService.recoverUser(ids);

    }


    @PostMapping("recoverFromDelete")
    @ApiOperation("??????????????????")
    @LogOperation("??????????????????")
    @RequiresPermissions("user:user:recoverFromDelete")
    public Result recoverFromDelete(@RequestBody String[] ids) {
        //????????????
        ValidatorUtils.validateEntity(ids, UpdateGroup.class, DefaultGroup.class);

        return userService.recoverFromDelete(ids);

    }

    @DeleteMapping
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("user:user:delete")
    public Result deleteReal(@RequestBody String[] ids) {
        //????????????
        AssertUtils.isArrayEmpty(ids, "id");

        userService.delete(ids);

        return new Result();
    }

    /*
    ?????????: ????????????
     */
    @DeleteMapping("logic")
    @ApiOperation("????????????")
    @LogOperation("????????????")
    @RequiresPermissions("user:user:deleteLogic")
    public Result deleteLogic(@RequestBody String[] ids) {
        //????????????
        AssertUtils.isArrayEmpty(ids, "id");

        return userService.deleteLogic(ids);

    }

    @GetMapping("export")
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("user:user:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserDTO> list = userService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, UserExcel.class);
    }

    @PostMapping("import/user")
    @ApiOperation("??????")
    @LogOperation("??????")
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
    @ApiOperation("??????")
    @LogOperation("??????")
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
    @ApiOperation("???????????????")
    @LogOperation("???????????????")
    @RequiresPermissions("user:user:verificationLogin")
    public Result verificationLogin(@RequestBody UserVerificationLoginDTO userVerificationLoginDTO) {
        //????????????
        ValidatorUtils.validateEntity(userVerificationLoginDTO, AddGroup.class, DefaultGroup.class);
        return userService.codeLoginValidate(userVerificationLoginDTO);
    }

    @PostMapping("sendCode")
    @ApiOperation("???????????????")
    @LogOperation("???????????????")
    @RequiresPermissions("user:user:sendCode")
    public Result sendCode(@RequestBody SendCodeDTO sendCodeDTO) {
        //????????????
        ValidatorUtils.validateEntity(sendCodeDTO, AddGroup.class, DefaultGroup.class);
        return userService.sendCode(sendCodeDTO);
    }

    @GetMapping("answerRight")
    public Result answerRight(@RequestParam("userId") String userId, @RequestParam("surveyId") String surveyId) {
        Result<SurveyDTO> group = surveyFeignClient.getGroup(surveyId);

        if (group.getData() == null) {
            return new Result().error("??????????????????");
        }
        if (group.getData().getReserved() == null || !(group.getData().getReserved().equals("1"))) {
            return new Result().error("???????????????????????????");
        }
        if ((group.getData().getStartTime() != null && group.getData().getStartTime().after(new Date())) || (group.getData().getEndTime() != null && group.getData().getEndTime().after(new Date()))) {
            return new Result().error("?????????????????????????????????");
        }
        if (group.getData().getUpdater() == null || memberService.have(userId, group.getData().getUpdater())) {
            return new Result().ok("??????");
        }
        return new Result().error("???????????????????????????????????????");
    }

}
