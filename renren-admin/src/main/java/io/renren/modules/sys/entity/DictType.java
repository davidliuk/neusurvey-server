/**
 * Copyright (c) 2019 人人开源 All rights reserved.
 * <p>
 * https://survey.neud.cn
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型
 *
 * @author David l729641074@163.com
 */
@Data
public class DictType {
    @JsonIgnore
    private Long id;
    private String dictType;
    private List<DictData> dataList = new ArrayList<>();
}
