package cn.neud.neusurvey.user.service;

import cn.neud.common.service.CrudService;
import cn.neud.common.utils.Result;
import cn.neud.neusurvey.dto.user.GroupHistoryDTO;
import cn.neud.neusurvey.dto.user.UserHistoryDTO;
import cn.neud.neusurvey.entity.user.GroupHistoryEntity;

/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
public interface GroupHistoryService extends CrudService<GroupHistoryEntity, GroupHistoryDTO> {
    Result saveGroupHistory(GroupHistoryDTO dto);

    Result updateGroupHistory(GroupHistoryDTO dto);

    Result deleteLogic(String[] ids);
}