package cn.neud.neusurvey.excel.survey;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-11
 */
@Data
public class RelateExcel {
    @Excel(name = "")
    private String surveyId;
    @Excel(name = "")
    private String groupId;
    @Excel(name = "")
    private Integer isDeleted;

}