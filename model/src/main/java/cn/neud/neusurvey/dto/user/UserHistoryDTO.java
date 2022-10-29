package cn.neud.neusurvey.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "user_history")
public class UserHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String id;

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "角色")
	private Integer role;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "手机号码")
	private String mobile;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "头像")
	private String header;

	@ApiModelProperty(value = "性别")
	private Integer gender;

	@ApiModelProperty(value = "生日")
	private Date birth;

	@ApiModelProperty(value = "所在城市")
	private String city;

	@ApiModelProperty(value = "职业")
	private String job;

	@ApiModelProperty(value = "个性签名")
	private String sign;

	@ApiModelProperty(value = "用户来源")
	private Integer sourceType;

	@ApiModelProperty(value = "管理人id")
	private String managedBy;

	@ApiModelProperty(value = "创建人")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新人")
	private String updater;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "软删除")
	private String isDeleted;

	@ApiModelProperty(value = "保留项json")
	private String reserved;


}