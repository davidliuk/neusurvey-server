package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.QuestionDTO;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.excel.survey.QuestionExcel;
import cn.neud.neusurvey.excel.user.UserExcel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ChoiceMapper.class })
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionEntity toQuestion(QuestionDTO QuestionDTO);

    QuestionDTO fromQuestion(QuestionEntity QuestionEntity);

    QuestionEntity fromExcel(QuestionExcel questionExcel);

}
