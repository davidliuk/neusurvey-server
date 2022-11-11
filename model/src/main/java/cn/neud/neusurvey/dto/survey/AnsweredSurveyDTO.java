package cn.neud.neusurvey.dto.survey;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnsweredSurveyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private SurveyDTO survey;

    private List<AnswerDTO> answerList;
}
