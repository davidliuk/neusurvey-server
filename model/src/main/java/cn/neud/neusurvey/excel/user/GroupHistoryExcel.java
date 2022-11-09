package cn.neud.neusurvey.excel.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class GroupHistoryExcel {
    @Excel(name = "主键id")
    private String id;
    @Excel(name = "群组id")
    private String groupId;
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