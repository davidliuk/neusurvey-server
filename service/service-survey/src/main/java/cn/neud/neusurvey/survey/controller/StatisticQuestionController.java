package cn.neud.neusurvey.survey.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.StatisticSurveyEntity;
import cn.neud.neusurvey.survey.service.StatisticQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/question")
@Api(tags="user_group")
public class StatisticQuestionController {

    @Resource
    StatisticQuestionService statisticQuestionService;

    @GetMapping("{id}")
    @ApiOperation("问题统计数据")
    @RequiresPermissions("survey:stasticQuestion:info")
    public Result groupStatistic(@PathVariable("id") String id){
        Result result = new Result();

        return statisticQuestionService.questionStatistic(id);

    }


}
