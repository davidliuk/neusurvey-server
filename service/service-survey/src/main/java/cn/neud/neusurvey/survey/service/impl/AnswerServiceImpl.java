package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.entity.survey.ChooseEntity;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.survey.service.AnswerService;
import cn.neud.neusurvey.survey.service.ChooseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.neud.common.service.impl.CrudServiceImpl;
import cn.neud.neusurvey.survey.dao.AnswerDao;
import cn.neud.neusurvey.dto.survey.AnswerDTO;
import cn.neud.neusurvey.entity.survey.AnswerEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * answer
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Service
public class AnswerServiceImpl extends CrudServiceImpl<AnswerDao, AnswerEntity, AnswerDTO> implements AnswerService {

    @Resource
    private ChooseService chooseService;

    @Override
    public QueryWrapper<AnswerEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<AnswerEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void save(AnswerDTO[] dtos) {
        for (AnswerDTO answer : dtos) {
            if (ObjectUtils.isNotEmpty(answer.getChoices())) {
                ChooseEntity choose = new ChooseEntity();
                choose.setSurveyId(answer.getSurveyId());
                choose.setUserId(answer.getUserId());
                for (String id : answer.getChoices()) {
                    choose.setChoiceId(id);
                    chooseService.insert(choose);
                }
                return;
            }
            super.save(answer);
        }
    }


}