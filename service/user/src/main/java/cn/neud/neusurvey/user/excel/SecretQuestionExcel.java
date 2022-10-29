package cn.neud.neusurvey.user.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class SecretQuestionExcel {
    @Excel(name = "主键id")
    private String id;
    @Excel(name = "题干")
    private String stem;
    @Excel(name = "答案")
    private String answer;
    @Excel(name = "用户id")
    private String userId;
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