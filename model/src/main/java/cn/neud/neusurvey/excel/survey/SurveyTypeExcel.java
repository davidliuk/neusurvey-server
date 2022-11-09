package cn.neud.neusurvey.excel.survey;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * survey_type
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class SurveyTypeExcel {
    @Excel(name = "主键id")
    private String id;
    @Excel(name = "问卷类别名字")
    private String name;
    @Excel(name = "描述")
    private String description;
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