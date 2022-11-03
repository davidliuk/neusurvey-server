package cn.neud.neusurvey.dto.survey;

import lombok.Data;

import java.util.ArrayList;

@Data
public class QuestionCreateDTO {
    String id;
    String stem;
    Integer questionType;
    ArrayList<QuestionCreateChoiceDTO> choices;

}
