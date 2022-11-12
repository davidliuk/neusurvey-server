package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

@Data
public class StatisticUserEntity {

    /**
     * 数量
     */
    private Integer total;
    /**
     * 时间
     */
    private Integer time;
    /**
     * 每类问卷百分比的情况
     */
    private StatisticChartEntiey questionTypes;
    /**
     * 每个用户发的问卷的百分比的问卷的情况
     */
    private StatisticChartEntiey questionUsers;






}
