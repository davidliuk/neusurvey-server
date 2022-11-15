package cn.neud.neusurvey.statistics.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/question")
@Api(tags="user_group")
public class StatisticsQuestionController {

    @Resource
    SurveyFeignClient surveyFeignClient;

    @GetMapping("{id}")
    @ApiOperation("问题统计数据")
    @RequiresPermissions("survey:stasticQuestion:info")
    public Result groupStatistic(@PathVariable("id") String id){

        Result result;

        result = surveyFeignClient.groupStatistic(id);
        System.out.println(result);

        return result;

    }


}
