/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://survey.neud.cn
 *
 * 版权所有，侵权必究！
 */

package cn.neud.neusurvey.oss.service;

import cn.neud.common.page.PageData;
import cn.neud.common.service.BaseService;
import cn.neud.neusurvey.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 * 
 * @author David l729641074@163.com
 */
public interface SysOssService extends BaseService<SysOssEntity> {

	PageData<SysOssEntity> page(Map<String, Object> params);
}
