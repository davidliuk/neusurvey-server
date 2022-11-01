/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://survey.neud.cn
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao;

import cn.neud.common.dao.BaseDao;
import io.renren.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 * 
 * @author David l729641074@163.com
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	

}
