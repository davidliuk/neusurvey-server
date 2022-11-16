package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.statistics.StatisticChartEntiey;
import cn.neud.neusurvey.entity.statistics.StatisticGroupEntry;
import cn.neud.neusurvey.entity.statistics.StatisticItemEntity;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import cn.neud.neusurvey.user.dao.MemberDao;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.user.dao.UserGroupDao;
import cn.neud.neusurvey.user.service.StatisticGroupService;
import cn.neud.neusurvey.user.service.StatisticSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticSurveyServiceImpl implements StatisticSurveyService {

    @Autowired
    UserDao userDao;

    @Override
    public List<String> provinceStatistic(List<String> userIds) {

        List<String> res=new ArrayList<>();

        for(int i=0;i<userIds.size();i++)
        {
            UserEntity userEntity=userDao.selectById(userIds.get(i));
            if(userEntity==null) continue;
            if(userEntity.getIsDeleted()!=null
                    &&userEntity.getIsDeleted().equals("1"))
                continue;

            if(userEntity.getCity()!=null)
                res.add(userEntity.getCity());
        }

        return res;
    }
}
