package cn.neud.neusurvey.entity.statistics;
import lombok.Data;

import java.util.List;

@Data
public class StatisticChartEntiey {
    /**
     * 数量
     */
    private int total;
    /**
     * 统计项
     */
    private List<StatisticItemEntity> list;

}
