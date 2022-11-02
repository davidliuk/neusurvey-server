package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

	@ApiModelProperty(value = "描述")
	private String description;
	/**
	 * 管理人id
	 */
	@ApiModelProperty(value = "问卷所有人")
	private String managedBy;

	@ApiModelProperty(value = "问卷开始时间")
	private Date startTime;

	@ApiModelProperty(value = "问卷结束时间")
	private Date endTime;

	@ApiModelProperty(value = "答卷次数限制")
	private Integer answerLimit;

	@ApiModelProperty(value = "问卷类型")
	private String typeId;

	@ApiModelProperty(value = "问题")
	private List<QuestionDTO> questions;

}
