package cn.neud.neusurvey.entity.statistics;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("statistic")
public class StatisticItemEntity {
    /**
     * 项目名
     */
    private String name;
    /**
     * 频次
     */
    private String value;
    /**
     * 百分比
     */
    private String percentage;
}
