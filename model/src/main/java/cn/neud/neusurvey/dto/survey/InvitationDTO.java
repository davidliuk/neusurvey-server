package cn.neud.neusurvey.dto.survey;

import cn.neud.neusurvey.dto.user.UserDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class InvitationDTO  implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String invitationCode;

    private String userId;

    private String creator;


}
