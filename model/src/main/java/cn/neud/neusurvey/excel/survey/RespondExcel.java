package cn.neud.neusurvey.excel.survey;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-09
 */
@Data
public class RespondExcel {
    @Excel(name = "答者id")
    private String userId;
    @Excel(name = "问卷id")
    private String surveyId;
    @Excel(name = "开始答卷时间")
    private Date startTime;
    @Excel(name = "结束答卷时间")
    private Date endTime;
//    @Excel(name = "回答次数")
//    private Date answerTime;

}
