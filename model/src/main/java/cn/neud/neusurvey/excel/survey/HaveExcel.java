package cn.neud.neusurvey.excel.survey;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * have
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class HaveExcel {
    @Excel(name = "问卷id")
    private String surveyId;
    @Excel(name = "问题id")
    private String questionId;
    @Excel(name = "下一个问题id")
    private String nextId;
    @Excel(name = "创建人")
    private String creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "更新人")
    private String updater;
    @Excel(name = "更新时间")
    private Date updateDate;
    @Excel(name = "软删除")
    private String isDeleted;
    @Excel(name = "保留项json")
    private String reserved;

}