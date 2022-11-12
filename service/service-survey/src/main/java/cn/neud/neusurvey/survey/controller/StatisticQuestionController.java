package cn.neud.neusurvey.survey.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.StatisticGroupEntry;
import cn.neud.neusurvey.survey.service.StatisticQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/group")
@Api(tags="user_group")
public class StatisticQuestionController {

    @Resource
    StatisticQuestionService statisticQuestionService;

    @GetMapping()
    @ApiOperation("问题统计数据")
    @RequiresPermissions("survey:stasticQuestion:info")
    public Result groupStatistic(@ApiIgnore @RequestParam String id){
        Result result = new Result();

        StatisticGroupEntry statisticQuestionEntry = statisticQuestionService.questionStatistic(id);

        return result.ok(statisticQuestionEntry);
    }


}
