package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticChartEntity {
    /**
     * 数量
     */
    private String total;
    /**
     * 统计图类型
     */
    private String chartType;
    /**
     * 统计项
     */
    private List<StatisticItemEntity> items;

}
