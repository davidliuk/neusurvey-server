package cn.neud.neusurvey.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "user_group")
public class UserGroupOperateUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String user_id;
    private String group_id;
}
