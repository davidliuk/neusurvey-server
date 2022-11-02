package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { QuestionMapper.class })
public interface SurveyMapper {

    SurveyMapper INSTANCE = Mappers.getMapper(SurveyMapper.class);

    SurveyEntity toSurvey(SurveyDTO SurveyDTO);

    SurveyDTO fromSurvey(SurveyEntity SurveyEntity);

}
