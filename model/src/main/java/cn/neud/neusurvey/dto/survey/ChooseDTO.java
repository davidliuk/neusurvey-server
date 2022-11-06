package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-05
 */
@Data
@ApiModel(value = "")
public class ChooseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private String surveyId;

	@ApiModelProperty(value = "")
	private String userId;

	@ApiModelProperty(value = "")
	private String choiceId;

	@ApiModelProperty(value = "")
	private Integer isDeleted;


}