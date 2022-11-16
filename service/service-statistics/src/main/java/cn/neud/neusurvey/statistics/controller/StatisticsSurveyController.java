package cn.neud.neusurvey.statistics.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/survey")
@Api(tags="user_group")
public class StatisticsSurveyController {

    @Resource
    SurveyFeignClient surveyFeignClient;

    @GetMapping("{id}")
    @ApiOperation("问题统计数据")
    @RequiresPermissions("survey:stasticSurvey:info")
    public Result surveyStatistic(@PathVariable("id") String id){

        Result result;

        result = surveyFeignClient.surveyStatistic(id);
        System.out.println(result);

        return result;

    }


}
