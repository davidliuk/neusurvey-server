package cn.neud.neusurvey.entity.statistics;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;

import lombok.Data;

import java.util.List;

@Data
public class StatisticGroupEntry {

   private String total;
   private String online;
   private StatisticChartEntiey heatmap;
   private List<StatisticChartEntiey> graphs ;
   private List<StatisticUserEntity> users;
}
