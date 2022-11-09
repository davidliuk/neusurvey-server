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
import cn.neud.neusurvey.dto.user.UserGroupDTO;
import cn.neud.neusurvey.dto.user.UserGroupOperateUserDTO;
import cn.neud.neusurvey.excel.user.UserGroupExcel;
import cn.neud.neusurvey.user.service.UserGroupService;
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
 * user_group
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("user/usergroup")
@Api(tags="user_group")
public class UserGroupController {
    @Resource
    private UserGroupService userGroupService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("user:usergroup:page")
    public Result<PageData<UserGroupDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<UserGroupDTO> page = userGroupService.page(params);

        return new Result<PageData<UserGroupDTO>>().ok(page);
    }
//    pageAnswerUser

//    @GetMapping("pageAnswerUser")
//    @ApiOperation("分页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
//            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
//            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
//            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
//    })
//    @RequiresPermissions("user:usergroup:page")
//    public Result<PageData<UserGroupDTO>> pageAnswerUser(@ApiIgnore @RequestParam Map<String, Object> params){
//        PageData<UserGroupDTO> page = userGroupService.pageAnswerUser(params);
//        return new Result<PageData<UserGroupDTO>>().ok(page);
//    }
    @GetMapping("/getGroupInfo")
    @ApiOperation("信息")
    @RequiresPermissions("user:usergroup:info")
    public Result<UserGroupDTO> get(@ApiIgnore @RequestParam String id){

        //雷世鹏:软删除判定和存在性检测
        if(!userGroupService.ifExists(id))
            return new Result().error("该群组不存在");
        if(userGroupService.ifDeleted(id))
            return new Result().error("该群组已经被删除");
 
        UserGroupDTO data = userGroupService.get(id);

        return new Result<UserGroupDTO>().ok(data);
    }
// 雷世鹏: 下面已经有这个的接口了
//    @PostMapping
//    @ApiOperation("保存")
//    @LogOperation("保存")
//    @RequiresPermissions("user:usergroup:save")
//    public Result save(@RequestBody UserGroupDTO dto){
//        //效验数据
//        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
//
//        userGroupService.save(dto);
//
//        return new Result();
//    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("user:usergroup:update")
    public Result update(@RequestBody UserGroupDTO dto){
//        System.out.println(dto);

        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        int result_code = userGroupService.updateGroup(dto);

        Result result = new Result();
        if (result_code == 444){
            result.setMsg("群组内含有用户无法修改！");
        }else {
            result.setMsg("修改成功！");
        }

        return result;
    }



    @DeleteMapping()
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("user:usergroup:delete")
    public Result delete(@RequestBody String[] ids){

        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

//        userGroupService.delete(ids);
        int result_code = userGroupService.deleteGroup(ids);
        Result result = new Result();
        if (result_code == 444){
            result.setMsg("群组内含有用户无法删除！");
        }else {
            result.setMsg("删除成功！");
        }

        return result;
    }

//    删除群组下的特定用户 软删除
@DeleteMapping("deleteUserById")
@ApiOperation("删除")
@LogOperation("删除")
@RequiresPermissions("user:usergroup:delete")
public Result deleteUserById(@RequestBody UserGroupOperateUserDTO dto){

    Result result = new Result();
    return userGroupService.deleteUserByPrimary(dto);
}

//    新增群组下的用户
@PostMapping("/addGroupUser")
@ApiOperation("保存")
@LogOperation("保存")
@RequiresPermissions("user:usergroup:save")
public Result addGroupUser(@RequestBody UserGroupOperateUserDTO dto){
    //效验数据
    ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

    return userGroupService.addGroupUser(dto);

}


    //雷世鹏:  新增群组
    @PostMapping()
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("user:usergroup:addGroup")
    public Result addGroup(@RequestBody UserGroupDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        return userGroupService.addGroup(dto);
    }

//    查找群组下的用户
@GetMapping("pageGroupUser")
@ApiOperation("分页")
@ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
})
@RequiresPermissions("user:usergroup:page")
public Result<PageData<UserDTO>> pageGroupUser(@ApiIgnore @RequestBody Map<String, Object> params){

    PageData<UserDTO> page = userGroupService.pageGroupUser(params);

    return new Result<PageData<UserDTO>>().ok(page);
}


////统计群组
////    新增群组下的用户
//@GetMapping("/StatisticGroup{id}")
//@ApiOperation("统计群组")
//@LogOperation("统计群组")
//@RequiresPermissions("user:usergroup:save")
//public Result StatisticGroup(@PathVariable("id") String id){
//    //效验数据
//    ValidatorUtils.validateEntity(id, AddGroup.class, DefaultGroup.class);
//    Result result = new Result();
//    int result_code = userGroupService.StatisticGroup(id);
//    result.setMsg("好耶");
//    return result;
//}

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("user:usergroup:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<UserGroupDTO> list = userGroupService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, UserGroupExcel.class);
    }

}