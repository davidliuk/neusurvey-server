/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://survey.neud.cn
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service;

import cn.neud.common.page.PageData;
import cn.neud.common.service.BaseService;
import io.renren.modules.sys.dto.SysDictTypeDTO;
import io.renren.modules.sys.entity.DictType;
import io.renren.modules.sys.entity.SysDictTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author David l729641074@163.com
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageData<SysDictTypeDTO> page(Map<String, Object> params);

    SysDictTypeDTO get(Long id);

    void save(SysDictTypeDTO dto);

    void update(SysDictTypeDTO dto);

    void delete(Long[] ids);

    /**
     * 获取所有字典
     */
    List<DictType> getAllList();

}