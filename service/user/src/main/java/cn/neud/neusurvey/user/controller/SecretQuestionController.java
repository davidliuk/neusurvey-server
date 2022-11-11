package cn.neud.neusurvey.user.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import cn.neud.neusurvey.excel.user.SecretQuestionExcel;
import cn.neud.neusurvey.mapper.user.SecretMapper;
import cn.neud.neusurvey.user.service.SecretQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("user/secretquestion")
@Api(tags="secret_question")
public class SecretQuestionController {

    @Resource
    private SecretQuestionService secretQuestionService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("user:secretquestion:page")
    public Result<PageData<SecretQuestionDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SecretQuestionDTO> page = secretQuestionService.page(params);

        return new Result<PageData<SecretQuestionDTO>>().ok(page);
    }

//    @GetMapping("{id}")
//    @ApiOperation("信息")
//    @RequiresPermissions("user:secretquestion:info")
//    public Result<SecretQuestionDTO> get(@PathVariable("id") String id){
//        SecretQuestionDTO data = secretQuestionService.get(id);
//
//        return new Result<SecretQuestionDTO>().ok(data);
//    }

    @GetMapping
    @ApiOperation("信息")
    @RequiresPermissions("user:secretquestion:info")
    public Result<List<SecretStemDTO>> get(@RequestParam("username") String username){
//        SecretQuestionDTO data = secretQuestionService.get(id);
        List<SecretQuestionDTO> data = secretQuestionService.list(username);
        List<SecretStemDTO> stemDTOs = new ArrayList<>();
        for (SecretQuestionDTO dto: data) {
            stemDTOs.add(SecretMapper.INSTANCE.fromSecret(dto));
        }
        return new Result<List<SecretStemDTO>>().ok(stemDTOs);
    }

//    @PostMapping
//    @ApiOperation("保存")
//    @LogOperation("保存")
//    @RequiresPermissions("user:secretquestion:save")
//    public Result save(@RequestBody SecretQuestionDTO dto){
//        //效验数据
//        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
//
//        secretQuestionService.save(dto);
//
//        return new Result();
//    }

    @PostMapping("/secret/retrieve")
    @ApiOperation("找回")
    @LogOperation("找回")
    @RequiresPermissions("user:secretquestion:save")
    public Result save(@RequestBody SecretDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        if (secretQuestionService.saveSecret(dto)) {
            return new Result();
        }

        return new Result().error("密保错误");
    }

    @PostMapping("")
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("user:secretquestion:save")
    public Result add(@RequestParam("username") String username ,@RequestBody SecretChangeDTO[] dtos){
        //效验数据
//        ValidatorUtils.validateEntity(dtos, AddGroup.class, DefaultGroup.class);

        secretQuestionService.add(username, dtos);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("user:secretquestion:update")
    public Result update(@RequestParam("username") String username, @RequestBody SecretChangeDTO[] dtos){
        //效验数据
//        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        secretQuestionService.update(username, dtos);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:secretquestion:delete")
    public Result delete(@RequestBody String[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        secretQuestionService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("user:secretquestion:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SecretQuestionDTO> list = secretQuestionService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SecretQuestionExcel.class);
    }

    @PostMapping("retrieve")
    @ApiOperation("手机号+验证码找回密码")
    @LogOperation("手机号+验证码找回密码")
    @RequiresPermissions("user:user:retrieve")
    public Result retrieve(@RequestBody UserVerificationLoginDTO userVerificationLoginDTO){
        //效验数据
        ValidatorUtils.validateEntity(userVerificationLoginDTO, AddGroup.class, DefaultGroup.class);
        return secretQuestionService.retrieve(userVerificationLoginDTO);
    }

    @PostMapping("sendCode")
    @ApiOperation("发送验证码")
    @LogOperation("发送验证码")
    @RequiresPermissions("user:user:sendCode")
    public Result sendCode(@RequestBody SendCodeDTO sendCodeDTO){
        //效验数据
        ValidatorUtils.validateEntity(sendCodeDTO, AddGroup.class, DefaultGroup.class);
        return secretQuestionService.sendCode(sendCodeDTO);
    }


}