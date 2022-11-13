package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticSurveyEntity {

   private Integer questionnaireTotal;
   private String questionnaireId;
   private String name;
   private String description;
   private String managedBy;
   private StatisticHeatMapEntity_WJC heatmap;
   private List<StatisticQuestionEntity_WJC> questions;
}
