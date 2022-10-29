package cn.neud.neusurvey.survey.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class SurveyExcel {
    @Excel(name = "主键id")
    private String id;
    @Excel(name = "问卷名字")
    private String name;
    @Excel(name = "管理人id")
    private String managedBy;
    @Excel(name = "描述")
    private String description;
    @Excel(name = "问卷开始时间")
    private Date startTime;
    @Excel(name = "问卷结束时间")
    private Date endTime;
    @Excel(name = "答卷次数限制")
    private Integer limit;
    @Excel(name = "问卷类型")
    private String typeId;
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