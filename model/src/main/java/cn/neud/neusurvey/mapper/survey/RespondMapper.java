package cn.neud.neusurvey.mapper.survey;

import cn.neud.neusurvey.dto.survey.RespondDTO;
import cn.neud.neusurvey.entity.survey.RespondEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { QuestionMapper.class })
public interface RespondMapper {

    RespondMapper INSTANCE = Mappers.getMapper(RespondMapper.class);

    RespondEntity toRespond(RespondDTO RespondDTO);

    RespondDTO fromRespond(RespondEntity RespondEntity);

}
