package cn.neud.neusurvey.survey.controller;

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
import cn.neud.neusurvey.dto.survey.AnsweredSurveyDTO;
import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.excel.survey.SurveyExcel;
import cn.neud.neusurvey.survey.service.SurveyService;
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
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("survey/survey")
@Api(tags = "survey")
public class SurveyController {
    @Resource
    private SurveyService surveyService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("survey:survey:page")
    public Result<PageData<SurveyDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SurveyDTO> page = surveyService.page(params);

        return new Result<PageData<SurveyDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("survey:survey:info")
    public Result<SurveyDTO> get(@PathVariable("id") String id) {
        //雷世鹏:软删除判定和存在性检测
        if (!surveyService.ifExists(id))
            return new Result().error("该问卷不存在");
        if (surveyService.ifDeleted(id))
            return new Result().error("该问卷已经被删除");

        SurveyDTO data = surveyService.get(id);
        return new Result<SurveyDTO>().ok(data);
    }

    @GetMapping("group/{id}")
    @ApiOperation("")
    @RequiresPermissions("survey:survey:info")
    public Result<SurveyDTO> getGroup(@PathVariable("id") String id) {
        //雷世鹏:软删除判定和存在性检测
        if (!surveyService.ifExists(id))
            return new Result().error("该问卷不存在");
        if (surveyService.ifDeleted(id))
            return new Result().error("该问卷已经被删除");

        SurveyDTO data = surveyService.get(id);
        return new Result<SurveyDTO>().ok(data);
    }

    @GetMapping("/getUserAnswerDerail")
    @ApiOperation("信息")
    @RequiresPermissions("survey:survey:info")
    public Result<AnsweredSurveyDTO> getUserAnswerDerail(@RequestParam String id, @RequestParam String userId) {

        //雷世鹏:软删除判定和存在性检测
//        if(!surveyService.ifExists(id))
//            return new Result().error("该问卷不存在");
//        if(surveyService.ifDeleted(id))
//            return new Result().error("该问卷已经被删除");

        AnsweredSurveyDTO data = surveyService.getUserAnswerDerail(id, userId);
        return new Result<AnsweredSurveyDTO>().ok(data);
    }


    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("survey:survey:save")
    public Result save(
            @RequestParam(value = "userId") String userId,
            @RequestBody SurveyDTO dto
    ) {
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        System.out.println(dto);
        surveyService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("survey:survey:update")
    public Result update(@RequestBody SurveyDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        Result result = surveyService.updateSurvey(dto);

        return result;
    }

    @DeleteMapping
    @ApiOperation("硬删除")
    @LogOperation("硬删除")
    @RequiresPermissions("survey:survey:delete")
    public Result delete(@RequestBody String[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");


        return surveyService.deleteSurvey(ids);

    }

    @DeleteMapping("logic")
    @ApiOperation("软删除")
    @LogOperation("软删除")
    @RequiresPermissions("survey:survey:deleteLogic")
    public Result deleteLogic(@RequestBody String[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        return surveyService.deleteSurveyLogic(ids);

    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("survey:survey:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SurveyDTO> list = surveyService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SurveyExcel.class);
    }

}