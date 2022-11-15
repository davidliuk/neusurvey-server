package cn.neud.neusurvey.user.client;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient(value = "neusurvey-survey" ,url = "localhost:8082")
//@RequestMapping("/oss")
@Repository
public interface SurveyFeignClient {

    @GetMapping("/statistics/respondent/{id}")
    @ApiOperation("答者统计数据")
    public Result respondentStatistic(@PathVariable("id") String id);

    @GetMapping("/statistics/respondent/getStatisticUserEntity{id}")
    @ApiOperation("答者统计数据")
    public StatisticUserEntity getStatisticUserEntity(@PathVariable("id") String id);


    @GetMapping("/statistics/question/{id}")
    @ApiOperation("问题统计数据")
    public Result groupStatistic(@PathVariable("id") String id);

}
