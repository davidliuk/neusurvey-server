package cn.neud.neusurvey.excel.survey;

import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuestionExcelTest {

    @Test
    public void testExcel() {
        QuestionExcel excel = new QuestionExcel();
        excel.setStem("heelo");
        ChoiceDTO choiceDTO = new ChoiceDTO();
        choiceDTO.setContent("sss");
        ChoiceDTO choiceDTO1 = new ChoiceDTO();
        choiceDTO.setContent("sass");
        excel.setChoices(Arrays.asList(choiceDTO, choiceDTO1));
    }

}