package cn.neud.neusurvey.entity.statistics;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("statistic")
public class StatisticChartEntity {
    /**
     * 数量
     */
    private String total;
    /**
     * 统计项
     */
    private List<StatisticItemEntity> list;


}
