package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-11
 */
@Data
@ApiModel(value = "")
public class RelateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private String surveyId;

	@ApiModelProperty(value = "")
	private String groupId;

	@ApiModelProperty(value = "")
	private Integer isDeleted;

}
