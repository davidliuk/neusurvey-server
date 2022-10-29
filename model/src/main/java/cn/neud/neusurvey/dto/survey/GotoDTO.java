package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * goto
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "goto")
public class GotoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "问卷id")
	private String surveyId;

	@ApiModelProperty(value = "问题id")
	private String questionId;

	@ApiModelProperty(value = "下一个问题id")
	private String choiceId;

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