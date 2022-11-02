package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@ApiModel(value = "question")
public class QuestionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private String id;

	@ApiModelProperty(value = "题干")
	private String stem;

	@ApiModelProperty(value = "问题类型")
	private Integer questionType;

	@ApiModelProperty(value = "下一题id")
	private String nextId;

	@ApiModelProperty(value = "选项")
	private List<ChoiceDTO> choices ;

}