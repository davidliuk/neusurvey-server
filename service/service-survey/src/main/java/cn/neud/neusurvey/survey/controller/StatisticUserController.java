package cn.neud.neusurvey.survey.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;
import cn.neud.neusurvey.survey.service.StatisticUserService;
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
@Api(tags="user_group")                           // ?
public class StatisticUserController {
    @Resource
    StatisticUserService statisticUserService;

    @GetMapping("{id}")
    @ApiOperation("答者统计数据")
    @RequiresPermissions("user:usergroup:info")
    public Result respondentStatistic(@PathVariable("id") String id) {
        Result result = new Result();

        StatisticUserEntity statisticUserEntity = statisticUserService.userStatistic(id);

        result.setData(statisticUserEntity);
        result.setMsg("成功");

        return result;
    }

    @GetMapping("getStatisticUserEntity{id}")
    @ApiOperation("答者统计数据")
    @RequiresPermissions("user:usergroup:info")
    public StatisticUserEntity getStatisticUserEntity(@PathVariable("id") String id) {

        StatisticUserEntity statisticUserEntity = statisticUserService.userStatistic(id);
        return statisticUserEntity;
    }
}
