package cn.neud.neusurvey.mapper.user;

import cn.neud.neusurvey.dto.survey.SurveyDTO;
import cn.neud.neusurvey.dto.user.SecretDTO;
import cn.neud.neusurvey.dto.user.SecretQuestionDTO;
import cn.neud.neusurvey.dto.user.SecretStemDTO;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.mapper.survey.QuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { QuestionMapper.class })
public interface SecretMapper {

    SecretMapper INSTANCE = Mappers.getMapper(SecretMapper.class);

    SecretStemDTO fromSecret(SecretQuestionDTO secretQuestionDTO);

}
