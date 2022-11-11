package cn.neud.neusurvey.dto.user;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "user")
public class UserPasswordResetDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String VerificationCode;

    @ApiModelProperty(value = "新密码")
    private String password;
}
