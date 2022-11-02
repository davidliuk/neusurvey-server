package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ChoiceMapper.class })
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionEntity toQuestion(QuestionDTO QuestionDTO);

    QuestionDTO fromQuestion(QuestionEntity QuestionEntity);

}
