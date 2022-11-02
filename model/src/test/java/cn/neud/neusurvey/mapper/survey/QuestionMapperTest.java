package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperTest {

    @Test
    void toQuestion() {
        ChoiceDTO choiceDTO = new ChoiceDTO();
        choiceDTO.setContent("abc");
        choiceDTO.setId("aaa");
        choiceDTO.setGoTo("123");
        ChoiceDTO choiceDTO1 = new ChoiceDTO();
        choiceDTO1.setContent("acc");
        choiceDTO1.setId("aaa");
        choiceDTO1.setGoTo("123");
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionType(1);
        questionDTO.setStem("hello");
        questionDTO.setChoices(Arrays.asList(choiceDTO, choiceDTO1));
        System.out.println(questionDTO);
        QuestionEntity question = QuestionMapper.INSTANCE.toQuestion(questionDTO);
        System.out.println(question);
    }

    @Test
    void fromQuestion() {
    }

}
