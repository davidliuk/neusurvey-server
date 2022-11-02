package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.ChoiceDTO;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChoiceMapper {

    ChoiceMapper INSTANCE = Mappers.getMapper(ChoiceMapper.class);

    ChoiceEntity toChoice(ChoiceDTO choiceDTO);

    ChoiceDTO fromChoice(ChoiceEntity choiceEntity);

}
