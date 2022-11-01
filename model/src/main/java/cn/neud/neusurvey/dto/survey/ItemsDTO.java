package cn.neud.neusurvey.dto.survey;

import io.swagger.annotations.ApiModelProperty;

public class ItemsDTO {
    @ApiModelProperty(value = "下一个问题")
    String nextId;
    @ApiModelProperty(value = "问题")
    QuestionDTO questions;
}
