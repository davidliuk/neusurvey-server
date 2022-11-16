package cn.neud.neusurvey.user.controller;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import cn.neud.neusurvey.user.service.StatisticSurveyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("statistics/province")
@Api(tags="user_group")
public class StatisticsSurveyController {

    @Resource
    StatisticSurveyService statisticSurveyService;

    @PostMapping
    @ApiOperation("省份统计数据")
    @RequiresPermissions("survey:stasticProvince:info")
    public List<String> provinceStatistic(@RequestBody List<String> userIds){

        return statisticSurveyService.provinceStatistic(userIds);

    }


}
