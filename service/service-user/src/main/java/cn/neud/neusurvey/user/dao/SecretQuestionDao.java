package cn.neud.neusurvey.user.dao;

import cn.neud.common.dao.BaseDao;
import cn.neud.neusurvey.entity.user.SecretQuestionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Mapper
public interface SecretQuestionDao extends BaseDao<SecretQuestionEntity> {
	
}