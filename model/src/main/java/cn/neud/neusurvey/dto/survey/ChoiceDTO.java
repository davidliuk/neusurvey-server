package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * choice
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "choice")
public class ChoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String id;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "跳转")
	private String  goTo;
}