package cn.neud.neusurvey.user.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.UserHistoryDTO;
import cn.neud.neusurvey.dto.user.UserHistoryUpdateDTO;
import cn.neud.neusurvey.entity.user.UserHistoryEntity;

/**
 * user_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface UserHistoryService extends CrudService<UserHistoryEntity, UserHistoryDTO> {

    Result saveUserHistory(UserHistoryDTO dto);

    Result updateUserHistory(UserHistoryUpdateDTO dto);

    Result deleteLogic(String[] ids);

}