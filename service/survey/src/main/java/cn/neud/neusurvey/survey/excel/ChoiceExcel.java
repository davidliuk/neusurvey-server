package cn.neud.neusurvey.survey.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * choice
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class ChoiceExcel {
    @Excel(name = "主键id")
    private String id;
    @Excel(name = "内容")
    private String content;
    @Excel(name = "题目id")
    private String belongTo;
    @Excel(name = "顺序")
    private Integer order;
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