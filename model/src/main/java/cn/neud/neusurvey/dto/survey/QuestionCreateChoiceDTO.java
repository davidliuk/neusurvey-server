package cn.neud.neusurvey.dto.survey;

import lombok.Data;

@Data
public class QuestionCreateChoiceDTO {
    String id;
    String content;
    String go_to;
}
