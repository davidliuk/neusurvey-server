/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://survey.neud.cn
 * <p>
 * 版权所有，侵权必究！
 */

package cn.neud.common.service;

import cn.neud.common.page.PageData;

import java.util.List;
import java.util.Map;

/**
 *  CRUD基础服务接口
 *
 * @author David l729641074@163.com
 */
public interface CrudService<T, D> extends BaseService<T> {

    PageData<D> page(Map<String, Object> params);

    List<D> list(Map<String, Object> params);

    D get(String id);

    void save(D dto);

    void update(D dto);

    void delete(String[] ids);

    void delete(Map<String, Object> params);

}