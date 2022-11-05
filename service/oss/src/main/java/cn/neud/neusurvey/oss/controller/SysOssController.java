/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://survey.neud.cn
 * <p>
 * 版权所有，侵权必究！
 */

package cn.neud.neusurvey.oss.controller;

import cn.neud.common.annotation.LogOperation;
import cn.neud.common.constant.Constant;
import cn.neud.common.exception.ErrorCode;
import cn.neud.common.page.PageData;
import cn.neud.common.utils.Result;
import cn.neud.common.validator.ValidatorUtils;
import com.google.gson.Gson;
import cn.neud.common.validator.group.AliyunGroup;
import cn.neud.common.validator.group.QcloudGroup;
import cn.neud.common.validator.group.QiniuGroup;
import cn.neud.neusurvey.oss.cloud.CloudStorageConfig;
import cn.neud.neusurvey.oss.cloud.OSSFactory;
import cn.neud.neusurvey.oss.entity.SysOssEntity;
import cn.neud.neusurvey.oss.service.SysOssService;
//import io.renren.modules.sys.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.*;

/**
 * 文件上传
 *
 * @author David l729641074@163.com
 */
@RestController
@RequestMapping("/oss")
@Api(tags = "文件上传")
public class SysOssController {
    @Resource
    private SysOssService sysOssService;
//    @Resource
//    private SysParamsService sysParamsService;

    private final static String KEY = Constant.CLOUD_STORAGE_CONFIG_KEY;

    @GetMapping("page")
    @ApiOperation(value = "分页")
    @RequiresPermissions("sys:oss:all")
    public Result<PageData<SysOssEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<SysOssEntity> page = sysOssService.page(params);

        return new Result<PageData<SysOssEntity>>().ok(page);
    }

//    @GetMapping("info")
//	@ApiOperation(value = "云存储配置信息")
//    @RequiresPermissions("sys:oss:all")
//    public Result<CloudStorageConfig> info(){
//        CloudStorageConfig config = sysParamsService.getValueObject(KEY, CloudStorageConfig.class);
//
//        return new Result<CloudStorageConfig>().ok(config);
//    }

    @PostMapping
    @ApiOperation(value = "保存云存储配置信息")
    @LogOperation("保存云存储配置信息")
    @RequiresPermissions("sys:oss:all")
    public Result saveConfig(@RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }

//		sysParamsService.updateValueByCode(KEY, new Gson().toJson(config));

        return new Result();
    }

    @PostMapping("upload/{path}")
    @ApiOperation(value = "上传文件")
    @RequiresPermissions("sys:oss:all")
    public Result<Map<String, Object>> upload(
            @RequestParam("file") MultipartFile file,
            @PathVariable("path") String path
    ) throws Exception {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        //上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//		String url = OSSFactory.build().uploadSuffix(file.getBytes(), extension);
        String url = OSSFactory.build().upload(file.getBytes(), path + "/" + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename());

//		//保存文件信息
//		SysOssEntity ossEntity = new SysOssEntity();
//		ossEntity.setUrl(url);
//		ossEntity.setCreateDate(new Date());
//		sysOssService.insert(ossEntity);

        Map<String, Object> data = new HashMap<>(1);
        data.put("src", url);

        return new Result<Map<String, Object>>().ok(data);
    }

    @DeleteMapping
    @ApiOperation(value = "删除")
    @LogOperation("删除")
    @RequiresPermissions("sys:oss:all")
    public Result delete(@RequestBody Long[] ids) {
        sysOssService.deleteBatchIds(Arrays.asList(ids));

        return new Result();
    }

}