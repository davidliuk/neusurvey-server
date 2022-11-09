package cn.neud.neusurvey.user.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.StatisticGroupEntry;
import cn.neud.neusurvey.user.service.StatisticGroupService;
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
public class StatisticGroupController {

    @Resource
    StatisticGroupService statisticGroupService;

    @GetMapping()
    @ApiOperation("群组统计数据")
    @RequiresPermissions("user:usergroup:info")
    public Result groupStatistic(@ApiIgnore @RequestParam String id){
        Result result = new Result();

        StatisticGroupEntry statisticGroupEntry = statisticGroupService.groupStatistic(id);

        result.setData(statisticGroupEntry);
        result.setMsg("好耶");




        return result;
    }


}
