package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticHeatMapEntity_WJC {
    /**
     * 数量
     */
    private Integer total;

    /**
     * 类型
     */
    private String chartType;

    /**
     * 统计项
     */
    private List<StatisticItemEntity_WJC> items;

}
