package cn.neud.neusurvey.mapper.user;

import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.excel.user.UserExcel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity fromExcel(UserExcel userExcel);

}
