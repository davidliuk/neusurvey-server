package cn.neud.neusurvey.excel.survey;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class QuestionExcel {
//    @Excel(name = "主键id")
//    private String id;
    @Excel(name = "题干")
    private String stem;

    @Excel(name = "正确选项个数")
    private Integer questionType;

    @Excel(name = "选项1")
    private String choice1;

    @Excel(name = "选项2")
    private String choice2;

    @Excel(name = "选项3")
    private String choice3;

    @Excel(name = "选项4")
    private String choice4;

    @Excel(name = "选项5")
    private String choice5;
//    @Excel(name = "备注json")
//    private String note;
//    @Excel(name = "创建人")
//    private String creator;
//    @Excel(name = "创建时间")
//    private Date createDate;
//    @Excel(name = "更新人")
//    private String updater;
//    @Excel(name = "更新时间")
//    private Date updateDate;
//    @Excel(name = "软删除")
//    private String isDeleted;
//    @Excel(name = "保留项json")
//    private String reserved;

}