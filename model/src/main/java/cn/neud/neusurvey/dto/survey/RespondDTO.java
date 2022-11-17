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
 * @since 1.0.0 2022-11-09
 */
@Data
@ApiModel(value = "")
public class RespondDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "答者id")
	private String userId;

	@ApiModelProperty(value = "问卷id")
	private String surveyId;

	@ApiModelProperty(value = "开始答卷时间")
	private Date startTime;

	@ApiModelProperty(value = "结束答卷时间")
	private Date endTime;

//	@ApiModelProperty(value = "回答次数")
//	private Date answerTime;

	@ApiModelProperty(value = "回答问题")
	private List<AnswerDTO> answers;

}
