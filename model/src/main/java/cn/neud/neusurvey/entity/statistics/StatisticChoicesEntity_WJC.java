package cn.neud.neusurvey.entity.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticChoicesEntity_WJC {
    /**
     * 内容
     */
    private String content;

    /**
     * 回答该选项的具体人数
     */
    private String choiceQuantity;

}
