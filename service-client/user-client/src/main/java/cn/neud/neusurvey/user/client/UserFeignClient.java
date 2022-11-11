package cn.neud.neusurvey.user.client;

import cn.neud.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "neusurvey-user")
//@RequestMapping("/oss")
@Repository
public interface UserFeignClient {

    @GetMapping("{id}")
    @ApiOperation("群组统计数据")
    public Result groupStatistic(@PathVariable("id") String id);

}
