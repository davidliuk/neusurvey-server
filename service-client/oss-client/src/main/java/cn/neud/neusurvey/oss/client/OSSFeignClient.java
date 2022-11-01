package cn.neud.neusurvey.oss.client;

import cn.neud.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(value = "neusurvey-oss")
@Repository
public interface OSSFeignClient {

    /**
     * 根据排班id获取预约下单数据
     */
    @PostMapping("upload/{path}")
    @ApiOperation(value = "上传文件")
    public Result<Map<String, Object>> upload(
            @RequestParam("file") MultipartFile file,
            @PathVariable("path") String path
    );

}
