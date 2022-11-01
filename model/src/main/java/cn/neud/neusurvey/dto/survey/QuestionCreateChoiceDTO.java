package cn.neud.neusurvey.dto.survey;

import lombok.Data;

@Data
public class QuestionCreateChoiceDTO {
    Integer id;
    String content;
    String  goTo;
}
