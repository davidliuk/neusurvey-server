package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


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

	@ApiModelProperty(value = "备注json")
	private String note;

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