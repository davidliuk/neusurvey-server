package cn.neud.neusurvey.excel.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
public class UserHistoryExcel {
    @Excel(name = "主键id")
    private String id;
    @Excel(name = "用户id")
    private String userId;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "角色")
    private Integer role;
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "手机号码")
    private String mobile;
    @Excel(name = "邮箱")
    private String email;
    @Excel(name = "头像")
    private String header;
    @Excel(name = "性别")
    private Integer gender;
    @Excel(name = "生日")
    private Date birth;
    @Excel(name = "所在城市")
    private String city;
    @Excel(name = "职业")
    private String job;
    @Excel(name = "个性签名")
    private String sign;
    @Excel(name = "用户来源")
    private Integer sourceType;
    @Excel(name = "管理人id")
    private String managedBy;
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