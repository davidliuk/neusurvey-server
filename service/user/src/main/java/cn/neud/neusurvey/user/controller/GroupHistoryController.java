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
import cn.neud.neusurvey.dto.user.GroupHistoryDTO;
import cn.neud.neusurvey.excel.user.GroupHistoryExcel;
import cn.neud.neusurvey.user.service.GroupHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("user/grouphistory")
@Api(tags="group_history")
public class GroupHistoryController {
    @Resource
    private GroupHistoryService groupHistoryService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("user:grouphistory:page")
    public Result<PageData<GroupHistoryDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<GroupHistoryDTO> page = groupHistoryService.page(params);

        return new Result<PageData<GroupHistoryDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("user:grouphistory:info")
    public Result<GroupHistoryDTO> get(@PathVariable("id") String id){
        GroupHistoryDTO data = groupHistoryService.get(id);

        return new Result<GroupHistoryDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("user:grouphistory:save")
    public Result save(@RequestBody GroupHistoryDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        return groupHistoryService.saveGroupHistory(dto);
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("user:grouphistory:update")
    public Result update(@RequestBody GroupHistoryDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        return  groupHistoryService.updateGroupHistory(dto);


    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:grouphistory:delete")
    public Result delete(@RequestBody String[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        groupHistoryService.delete(ids);

        return new Result();
    }

    @DeleteMapping("logic")
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:grouphistory:deleteLogic")
    public Result deleteLogic(@RequestBody String[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        return groupHistoryService.deleteLogic(ids);

    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("user:grouphistory:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<GroupHistoryDTO> list = groupHistoryService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, GroupHistoryExcel.class);
    }

}