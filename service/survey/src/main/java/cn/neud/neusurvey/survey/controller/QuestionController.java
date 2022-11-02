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
import cn.neud.neusurvey.dto.survey.QuestionCreateDTO;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.survey.excel.QuestionExcel;
import cn.neud.neusurvey.survey.service.QuestionService;
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
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@RestController
@RequestMapping("survey/question")
@Api(tags = "question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("survey:question:page")
    public Result<PageData<QuestionDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<QuestionDTO> page = questionService.page(params);

        return new Result<PageData<QuestionDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("survey:question:info")
    public Result<QuestionDTO> get(@PathVariable("id") String id) {
        QuestionDTO data = questionService.get(id);

        return new Result<QuestionDTO>().ok(data);
    }
//  郭帅乐：这个创建问题已经有了
//    @PostMapping
//    @ApiOperation("保存")
//    @LogOperation("保存")
//    @RequiresPermissions("survey:question:save")
//    public Result save(@RequestBody QuestionDTO dto){
//        //效验数据
//        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
//
//        questionService.save(dto);
//
//        return new Result();
//    }
//  雷世鹏：这个修改问题已经做了
//    @PutMapping
//    @ApiOperation("修改")
//    @LogOperation("修改")
//    @RequiresPermissions("survey:question:update")
//    public Result update(@RequestBody QuestionDTO dto){
//        //效验数据
//        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
//
//        questionService.update(dto);
//
//        return new Result();
//    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("survey:question:delete")
    public Result delete(@RequestBody String[] ids) {
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        questionService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("survey:question:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<QuestionDTO> list = questionService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, QuestionExcel.class);
    }

    @PostMapping("addQuestion")
    @ApiOperation("创建问题")
    @LogOperation("创建问题")
    @RequiresPermissions("survey:question:add")
    public Result createQuestion(@RequestParam(value = "userId") String userId, @RequestBody QuestionCreateDTO questionCreateDTO) {
        //效验数据
//        ValidatorUtils.validateEntity(questionCreateDTO, UpdateGroup.class, DefaultGroup.class);
        System.out.println("userId" + userId);

        int result_code = questionService.createQuestion(userId, questionCreateDTO);
        Result result = new Result();
        if (result_code == 444) {
            result.setMsg("创建失败 内容已存在");
        } else {
            result.setMsg("创建成功");
        }
        return result;
    }


    /*
    创建人:雷世鹏
    功能:修改问题
     */
    @PostMapping()
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("survey:question:update_question")
    public Result updateQuestion(@RequestBody QuestionDTO dto, @RequestParam(name = "userId") String updaterId){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        //注意:这里只传了goto,因此跳转只局限于问卷内部,且choice的ID在全集唯一,因此一定能找到
        return questionService.updateQuestion(dto,updaterId);

    }

}