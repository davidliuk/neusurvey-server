package cn.neud.neusurvey.survey.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.survey.GotoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * goto
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface GotoDao extends BaseDao<GotoEntity> {


    GotoEntity selectByPrimary(GotoEntity gotoEntity);
}