package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "survey")
public class SurveyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String id;

	@ApiModelProperty(value = "问卷名字")
	private String name;

	@ApiModelProperty(value = "管理人id")
	private String managedBy;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "问卷开始时间")
	private Date startTime;

	@ApiModelProperty(value = "问卷结束时间")
	private Date endTime;

	@ApiModelProperty(value = "答卷次数限制")
	private Integer limit;

	@ApiModelProperty(value = "问卷类型")
	private String typeId;

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