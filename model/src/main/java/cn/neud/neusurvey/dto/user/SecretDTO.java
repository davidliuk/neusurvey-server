package cn.neud.neusurvey.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class SecretDTO {
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private List<Answer> answers;
}

