package cn.neud.neusurvey.survey.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.neud.common.annotation.LogOperation;
import cn.neud.common.constant.Constant;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.ExcelUtils;
import cn.neud.common.utils.Result;
import cn.neud.common.utils.UUIDUtil;
import cn.neud.common.validator.AssertUtils;
import cn.neud.common.validator.ValidatorUtils;
import cn.neud.common.validator.group.DefaultGroup;
import cn.neud.common.validator.group.UpdateGroup;
import cn.neud.neusurvey.dto.survey.QuestionCreateDTO;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.excel.survey.QuestionExcel;
import cn.neud.neusurvey.excel.user.UserExcel;
import cn.neud.neusurvey.mapper.survey.QuestionMapper;
import cn.neud.neusurvey.mapper.user.UserMapper;
import cn.neud.neusurvey.survey.service.ChoiceService;
import cn.neud.neusurvey.survey.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
    @Resource
    private QuestionService questionService;

    @Resource
    private ChoiceService choiceService;

//    @Resource
//    private OSSFeignClient ossFeignClient;

    @GetMapping("page")
    @ApiOperation("??????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "??????????????????1??????", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "?????????????????????", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "????????????", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "????????????????????????(asc???desc)", paramType = "query", dataType = "String")
    })
    @RequiresPermissions("survey:question:page")
    public Result<PageData<QuestionDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<QuestionDTO> page = questionService.page(params);

        return new Result<PageData<QuestionDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("??????")
    @RequiresPermissions("survey:question:info")
    public Result<QuestionDTO> get(@PathVariable("id") String id) {
        QuestionDTO data = questionService.get(id);

        return new Result<QuestionDTO>().ok(data);
    }

//  ??????????????????????????????????????????
//    @PostMapping
//    @ApiOperation("??????")
//    @LogOperation("??????")
//    @RequiresPermissions("survey:question:save")
//    public Result save(@RequestBody QuestionDTO dto){
//        //????????????
//        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
//
//        questionService.save(dto);
//
//        return new Result();
//    }
//  ??????????????????????????????????????????
//    @PutMapping
//    @ApiOperation("??????")
//    @LogOperation("??????")
//    @RequiresPermissions("survey:question:update")
//    public Result update(@RequestBody QuestionDTO dto){
//        //????????????
//        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
//
//        questionService.update(dto);
//
//        return new Result();
//    }

    @DeleteMapping
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("survey:question:delete")
    public Result delete(@RequestBody String[] ids){

        //????????????
        AssertUtils.isArrayEmpty(ids, "id");

        return questionService.deleteQuestion(ids);

    }

    @GetMapping("export")
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("survey:question:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<QuestionDTO> list = questionService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, QuestionExcel.class);
    }

    @PostMapping("import")
    @ApiOperation("??????")
    @LogOperation("??????")
//    @RequiresPermissions("questionnaire:teacher:save")
    public Result importExcel(@RequestBody MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        List<QuestionExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), QuestionExcel.class, importParams);
        for (QuestionExcel questionExcel : list) {
            QuestionEntity question = QuestionMapper.INSTANCE.fromExcel(questionExcel);
            question.setId(UUIDUtil.getOneUUID());
            questionService.insert(question);

            if (StringUtils.isBlank(questionExcel.getChoice1())) {
                continue;
            }
            ChoiceEntity choice1 = new ChoiceEntity();
            choice1.setId(UUIDUtil.getOneUUID());
            choice1.setContent(questionExcel.getChoice1());
            choice1.setBelongTo(question.getId());
            choice1.setChoiceOrder(1);
            choiceService.insert(choice1);

            if (StringUtils.isBlank(questionExcel.getChoice2())) {
                continue;
            }
            ChoiceEntity choice2 = new ChoiceEntity();
            choice2.setId(UUIDUtil.getOneUUID());
            choice2.setContent(questionExcel.getChoice2());
            choice2.setBelongTo(question.getId());
            choice2.setChoiceOrder(2);
            choiceService.insert(choice2);

            if (StringUtils.isBlank(questionExcel.getChoice3())) {
                continue;
            }
            ChoiceEntity choice3 = new ChoiceEntity();
            choice3.setId(UUIDUtil.getOneUUID());
            choice3.setContent(questionExcel.getChoice3());
            choice3.setBelongTo(question.getId());
            choice3.setChoiceOrder(3);
            choiceService.insert(choice3);

            if (StringUtils.isBlank(questionExcel.getChoice4())) {
                continue;
            }
            ChoiceEntity choice4 = new ChoiceEntity();
            choice4.setId(UUIDUtil.getOneUUID());
            choice4.setContent(questionExcel.getChoice4());
            choice4.setBelongTo(question.getId());
            choice4.setChoiceOrder(4);
            choiceService.insert(choice4);

            if (StringUtils.isBlank(questionExcel.getChoice5())) {
                continue;
            }
            ChoiceEntity choice5 = new ChoiceEntity();
            choice5.setId(UUIDUtil.getOneUUID());
            choice5.setContent(questionExcel.getChoice5());
            choice5.setBelongTo(question.getId());
            choice5.setChoiceOrder(5);
            choiceService.insert(choice5);

            question.setChoices(Arrays.asList(choice1, choice2, choice3, choice4, choice5));
            System.out.println(questionExcel);
            System.out.println(question);
        }
        return new Result();
    }

    @PostMapping("addQuestion")
    @ApiOperation("????????????")
    @LogOperation("????????????")
    @RequiresPermissions("survey:question:add")
    public Result createQuestion(@RequestParam(value = "userId") String userId, @RequestBody QuestionCreateDTO questionCreateDTO) {
        //????????????
        ValidatorUtils.validateEntity(questionCreateDTO, UpdateGroup.class, DefaultGroup.class);
        System.out.println("userId" + userId);

        int result_code = questionService.createQuestion(userId, questionCreateDTO);
        Result result = new Result();
        if (result_code == 444) {
            result.setMsg("???????????? ???????????????");
        } else {
            result.setMsg("????????????");
        }
        return result;
    }


    /*
    ?????????:?????????
    ??????:????????????
     */
    @PostMapping("updateQuestion")
    @ApiOperation("??????")
    @LogOperation("??????")
    @RequiresPermissions("survey:question:update_question")
    public Result updateQuestion(@RequestBody QuestionDTO dto, @RequestParam(name = "userId") String updaterId){
        //????????????
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        //??????:???????????????goto,????????????????????????????????????,???choice???ID???????????????,?????????????????????
        return questionService.updateQuestion(dto,updaterId);

    }

    /*
    ?????????:?????????
    ??????:????????????????????????
    */
    @PostMapping("getQuestions")
    @ApiOperation("????????????????????????")
    @LogOperation("????????????????????????")
    @RequiresPermissions("survey:question:getQuestion")
    public Result updateQuestion(@RequestBody String[] ids){
        //????????????
        AssertUtils.isArrayEmpty(ids, "id");

        //??????:???????????????goto,????????????????????????????????????,???choice???ID???????????????,?????????????????????
        return questionService.getQuestion(ids);

    }
}