package cn.neud.neusurvey.user.client;

import cn.neud.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "neusurvey-user", name = "neusurvey-user", url = "localhost:8081")
//@RequestMapping("/user")
@Repository
public interface UserFeignClient {

    @GetMapping("/statistics/group/{id}")
    @ApiOperation("群组统计数据")
    public Result groupStatistic(@PathVariable("id") String id);


}
