package cn.neud.neusurvey.survey.controller;

import cn.neud.common.annotation.LogOperation;
import cn.neud.common.constant.Constant;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.ExcelUtils;
import cn.neud.common.utils.Result;
import cn.neud.common.validator.AssertUtils;
import cn.neud.common.validator.ValidatorUtils;
import cn.neud.common.validator.group.DefaultGroup;
import cn.neud.common.validator.group.UpdateGroup;
import cn.neud.neusurvey.dto.survey.AnswerDTO;
import cn.neud.neusurvey.excel.survey.AnswerExcel;
import cn.neud.neusurvey.survey.service.AnswerService;
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
 * answer
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("survey/answer")
@Api(tags="answer")
public class AnswerController {
    @Resource
    private AnswerService answerService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("survey:answer:page")
    public Result<PageData<AnswerDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<AnswerDTO> page = answerService.page(params);

        return new Result<PageData<AnswerDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("survey:answer:info")
    public Result<AnswerDTO> get(@PathVariable("id") String id){
        AnswerDTO data = answerService.get(id);

        return new Result<AnswerDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("survey:answer:save")
    public Result save(@RequestBody AnswerDTO[] dtos){
        //效验数据
//        ValidatorUtils.validateEntity(dtos, AddGroup.class, DefaultGroup.class);

        answerService.save(dtos);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("survey:answer:update")
    public Result update(@RequestBody AnswerDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        answerService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("survey:answer:delete")
    public Result delete(@RequestBody String[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        answerService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("survey:answer:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<AnswerDTO> list = answerService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, AnswerExcel.class);
    }

}