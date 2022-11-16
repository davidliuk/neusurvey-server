package cn.neud.neusurvey.survey.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.survey.service.StatisticSurveyService;
import cn.neud.neusurvey.survey.service.SurveyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/survey")
@Api(tags="user_group")
public class StatisticsSurveyController {

    @Resource
    StatisticSurveyService statisticSurveyService;

    @GetMapping("{id}")
    @ApiOperation("问卷统计数据")
    @RequiresPermissions("survey:stasticSurvey:info")
    public Result surveyStatistic(@PathVariable("id") String id){

        Result result;

        result = statisticSurveyService.surveyStatistic(id);

        return result;

    }


}
