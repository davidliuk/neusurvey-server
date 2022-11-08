package cn.neud.neusurvey.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * user_group
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "user_group")
public class UserGroupDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String id;

	@ApiModelProperty(value = "组名")
	private String groupname;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "答者id")
	private String[] userIds;

	@ApiModelProperty(value = "创建人")
	private String creator;

}