package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticGroupEntry {

   private String total;
   private String online;
   private StatisticChartEntity heatmap;
   private List<StatisticChartEntity> graphs ;
   private List<StatisticUserEntity> users;
}
