package cn.neud.neusurvey.statistics.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/respondent")
@Api(tags = "user_group")                           // ?
public class StatisticsUserController {
    @Resource
    SurveyFeignClient surveyFeignClient;

    @GetMapping("/{id}")
    @ApiOperation("答者统计数据")
    @RequiresPermissions("user:usergroup:info")
    public Result respondentStatistic(@PathVariable("id") String id) {
        Result result = new Result();

        result = surveyFeignClient.respondentStatistic(id);

        return result;
    }

}
