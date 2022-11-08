package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

@Data
public class StatisticGroupEntry {

   private String total;
   private String online;
   private StatisticChartEntity heatmap;
   private StatisticChartEntity graphs ;
   private StatisticUserEntity users;
}
