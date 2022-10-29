package cn.neud.neusurvey.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("user_history")
public class UserHistoryEntity {

    /**
     * 主键id
     */
	private String id;
    /**
     * 用户id
     */
	private String userId;
    /**
     * 密码
     */
	private String password;
    /**
     * 角色
     */
	private Integer role;
    /**
     * 昵称
     */
	private String nickname;
    /**
     * 手机号码
     */
	private String mobile;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 头像
     */
	private String header;
    /**
     * 性别
     */
	private Integer gender;
    /**
     * 生日
     */
	private Date birth;
    /**
     * 所在城市
     */
	private String city;
    /**
     * 职业
     */
	private String job;
    /**
     * 个性签名
     */
	private String sign;
    /**
     * 用户来源
     */
	private Integer sourceType;
    /**
     * 管理人id
     */
	private String managedBy;
    /**
     * 创建人
     */
	private String creator;
    /**
     * 创建时间
     */
	private Date createDate;
    /**
     * 更新人
     */
	private String updater;
    /**
     * 更新时间
     */
	private Date updateDate;
    /**
     * 软删除
     */
	private String isDeleted;
    /**
     * 保留项json
     */
	private String reserved;
}