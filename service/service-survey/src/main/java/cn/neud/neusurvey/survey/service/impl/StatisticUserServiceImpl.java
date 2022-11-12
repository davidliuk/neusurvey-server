package cn.neud.neusurvey.survey.service.impl;

import cn.neud.neusurvey.entity.statistics.StatisticChartEntiey;
import cn.neud.neusurvey.entity.statistics.StatisticItemEntity;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;
import cn.neud.neusurvey.survey.dao.UserDao;
import cn.neud.neusurvey.survey.service.StatisticUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticUserServiceImpl implements StatisticUserService {

    @Autowired
    UserDao userDao;

    @Override
    public StatisticUserEntity userStatistic(String id) {

        // 该答者所答卷的数量
        int total = userDao.countRespond(id);
        // 该答者答卷的总时间
        List<Integer> times = userDao.respondTime(id);
        int allTime = 0;
        for (int i = 0;i<times.size();i++){
            allTime += times.get(i);
        }
        StatisticUserEntity statisticUserEntity = new StatisticUserEntity();

        // questionTypes:  1.数量total+ 2.list：{{项目名，频次，百分比}，{项目名，频次，百分比}，{项目名，频次，百分比}}
        StatisticChartEntiey questionTypes = new StatisticChartEntiey();

        List<StatisticItemEntity> questionTypeList =  userDao.statisticSurveyType(id);;
        for (int i = 0; i < questionTypeList.size(); i++) {
            questionTypeList.get(i).setPercentage((Double.parseDouble(questionTypeList.get(i).getValue()) / total) * 100 + "%");
        }

        questionTypes.setTotal(total);
        questionTypes.setList(questionTypeList);

        // 根据所答问卷的创建者统计
        // questionUsers:  1.数量total+ 2.list：{{项目名，频次，百分比}，{项目名，频次，百分比}，{项目名，频次，百分比}}
        StatisticChartEntiey questionUsers = new StatisticChartEntiey();

        List<StatisticItemEntity> questionUserList =  userDao.statisticUserType(id);
        for (int i = 0; i < questionUserList.size(); i++) {
            questionUserList.get(i).setPercentage((Double.parseDouble(questionUserList.get(i).getValue()) / total) * 100 + "%");
        }

        questionUsers.setTotal(total);
        questionUsers.setList(questionUserList);

        statisticUserEntity.setTotal(total);
        statisticUserEntity.setTime(allTime);
        statisticUserEntity.setQuestionTypes(questionTypes);
        statisticUserEntity.setQuestionUsers(questionUsers);
        return statisticUserEntity;
    }
}
