package cn.neud.neusurvey.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "group_history")
public class GroupHistoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String id;

	@ApiModelProperty(value = "群组id")
	private String groupId;

	@ApiModelProperty(value = "创建人")
	private String creator;

	@ApiModelProperty(value = "保留项json")
	private String reserved;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "组名")
	private String groupname;


}