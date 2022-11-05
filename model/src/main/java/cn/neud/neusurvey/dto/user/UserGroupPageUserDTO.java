package cn.neud.neusurvey.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "user_group")
public class UserGroupPageUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String page;
    private String size;
    private String orderField;
    private String order;
    private String group_id;
}
