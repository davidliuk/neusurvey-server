package cn.neud.neusurvey.statistics.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.user.client.UserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("statistics/group")
@Api(tags = "user_group")
public class StatisticsGroupController {

    @Resource
    UserFeignClient userFeignClient;


    @GetMapping("{id}")
    @ApiOperation("群组统计数据")
    @RequiresPermissions("user:usergroup:info")
//    public Result questionStatistic(@ApiIgnore @RequestParam String id){
    public Result groupStatistic(@PathVariable("id") String id) {

        Result result = userFeignClient.groupStatistic(id);

        return result;
    }


}