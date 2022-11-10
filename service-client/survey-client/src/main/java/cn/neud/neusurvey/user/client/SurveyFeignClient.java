package cn.neud.neusurvey.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

@FeignClient(value = "neusurvey-survey")
//@RequestMapping("/oss")
@Repository
public class SurveyFeignClient {

}
