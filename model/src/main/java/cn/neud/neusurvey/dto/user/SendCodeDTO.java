package cn.neud.neusurvey.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "user")
public class SendCodeDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号")
    private String phone;
}
