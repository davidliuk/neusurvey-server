package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ChoiceMapperTest {

    @Test
    void toChoice() {
        ChoiceDTO choiceDTO = new ChoiceDTO();
        choiceDTO.setContent("abc");
        choiceDTO.setId("aaa");
        choiceDTO.setGoTo(Arrays.asList("123", "234"));
        System.out.println(choiceDTO);

        ChoiceEntity choiceEntity = ChoiceMapper.INSTANCE.toChoice(choiceDTO);
        System.out.println(choiceEntity);
    }

}