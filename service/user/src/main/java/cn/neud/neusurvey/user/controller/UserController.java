package cn.neud.neusurvey.user.controller;

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
import cn.neud.neusurvey.dto.user.UserDTO;
import cn.neud.neusurvey.dto.user.UserLoginDTO;
import cn.neud.neusurvey.dto.user.UserRegisterDTO;
import cn.neud.neusurvey.entity.user.UserLoginEntity;
import cn.neud.neusurvey.user.excel.UserExcel;
import cn.neud.neusurvey.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
@Api(tags="user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("user:user:page")
    public Result<PageData<UserDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<UserDTO> page = userService.page(params);

        return new Result<PageData<UserDTO>>().ok(page);
    }

    @GetMapping("")
    @ApiOperation("信息")
    @RequiresPermissions("user:user:info")
    public String hello(){
        return "hello";
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("user:user:info")
    public Result<UserDTO> get(@PathVariable("id") Long id){
        UserDTO data = userService.get(id);
        Result<UserDTO> result = new Result<UserDTO>().ok(data);
        result.setMsg("10086");
        return result;
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("user:user:save")
    public Result save(@RequestBody UserDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        userService.save(dto);

        return new Result();
    }


    @PostMapping("login/")
    @ApiOperation("登录")
    @LogOperation("登录")
    @RequiresPermissions("user:user:login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        //效验数据
        ValidatorUtils.validateEntity(userLoginDTO, AddGroup.class, DefaultGroup.class);
        return userService.loginValidate(userLoginDTO);
    }

    @PostMapping("register")
    @ApiOperation("注册")
    @LogOperation("注册")
    @RequiresPermissions("user:user:register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        //效验数据
        ValidatorUtils.validateEntity(userRegisterDTO, AddGroup.class, DefaultGroup.class);
        return userService.register(userRegisterDTO);
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("user:user:update")
    public Result update(@RequestBody UserDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        userService.update(dto);

        return new Result();
    }



    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:user:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        userService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("user:user:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserDTO> list = userService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, UserExcel.class);
    }

}