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
import cn.neud.neusurvey.dto.user.UserHistoryDTO;
import cn.neud.neusurvey.dto.user.UserHistoryUpdateDTO;
import cn.neud.neusurvey.user.excel.UserHistoryExcel;
import cn.neud.neusurvey.user.service.UserHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("user/userhistory")
@Api(tags="user_history")
public class UserHistoryController {
    @Resource
    private UserHistoryService userHistoryService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("user:userhistory:page")
    public Result<PageData<UserHistoryDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<UserHistoryDTO> page = userHistoryService.page(params);

        return new Result<PageData<UserHistoryDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("user:userhistory:info")
    public Result<UserHistoryDTO> get(@PathVariable("id") String id){
        UserHistoryDTO data = userHistoryService.get(id);

        return new Result<UserHistoryDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("user:userhistory:save")
    public Result save(@RequestBody UserHistoryDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        return userHistoryService.saveUserHistory(dto);
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("user:userhistory:update")
    public Result update(@RequestBody UserHistoryUpdateDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        return userHistoryService.updateUserHistory(dto);
    }



    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:userhistory:delete")
    public Result delete(@RequestBody String[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        userHistoryService.delete(ids);

        return new Result();
    }

    @DeleteMapping("logic")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:userhistory:deleteLogic")
    public Result deleteLogic(@RequestBody String[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        return userHistoryService.deleteLogic(ids);

    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("user:userhistory:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserHistoryDTO> list = userHistoryService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, UserHistoryExcel.class);
    }

}