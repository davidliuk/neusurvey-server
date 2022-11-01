/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://survey.neud.cn
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.oss.dao;

import cn.neud.common.dao.BaseDao;
import io.renren.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 * 
 * @author David l729641074@163.com
 */
@Mapper
public interface SysOssDao extends BaseDao<SysOssEntity> {
	
}
