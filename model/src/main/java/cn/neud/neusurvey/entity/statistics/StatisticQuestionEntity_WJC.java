package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticQuestionEntity_WJC {

    /**
     * 数量
     */
    private Integer total;

    /**
     * id
     */
    private String id;

    /**
     * 时间
     */
    private Integer questionType;

    /**
     * 题干
     */
    private String stem;

    /**
     * 返回字符串"bar"
     */
    private String chartType;

    /**
     * 每个用户发的问卷的百分比的问卷的情况
     */
    private List<StatisticChoicesEntity_WJC> questionUsers;

}
