package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.statistics.StatisticGroupEntry;
import cn.neud.neusurvey.entity.statistics.StatisticItemEntity;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;
import cn.neud.neusurvey.entity.statistics.StatisticChartEntiey;
import cn.neud.neusurvey.user.client.SurveyFeignClient;
import cn.neud.neusurvey.user.dao.MemberDao;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.user.dao.UserGroupDao;
import cn.neud.neusurvey.user.service.StatisticGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticGroupServiceImpl implements StatisticGroupService {

    @Autowired
    UserGroupDao userGroupDao;

    @Autowired
    UserDao userDao;

    @Resource
    MemberDao memberDao;

    @Resource
    SurveyFeignClient surveyFeignClient;





    @Override
    public StatisticGroupEntry groupStatistic(String id) {


        String total = String.valueOf(userDao.countGroupUser(id,""));
        String online = "99";
        StatisticGroupEntry statisticGroupEntry = new StatisticGroupEntry();
        StatisticChartEntiey heatmap = new StatisticChartEntiey();
        List<StatisticChartEntiey> graphs = new ArrayList<>();
        List<StatisticUserEntity>users = new ArrayList<>();

        List<StatisticItemEntity> heatmapList =  userDao.statisticHeatmap(id);;
        for (int i = 0; i < heatmapList.size(); i++) {
            heatmapList.get(i).setPercentage((Double.parseDouble(heatmapList.get(i).getValue()) / Double.parseDouble(total)) * 100 + "%");
        }
        System.out.println(heatmapList);
        heatmap.setTotal(Integer.parseInt(total));
        heatmap.setList(heatmapList);

        StatisticChartEntiey genderChartEntity  = new StatisticChartEntiey();
        List<StatisticItemEntity> genderList =userDao.statisticByGender(id);
        for (int i = 0; i < genderList.size(); i++) {
            genderList.get(i).setPercentage((Double.parseDouble(genderList.get(i).getValue()) / Double.parseDouble(total)) *100 + "%");
        }

        System.out.println(genderList);
        genderChartEntity.setTotal(Integer.parseInt(total));
        genderChartEntity.setList(genderList);
        graphs.add(genderChartEntity);

        StatisticChartEntiey birthChartEntity  = new StatisticChartEntiey();
        List<StatisticItemEntity> birthList =userDao.statisticByBirth(id);
        for (int i = 0; i < birthList.size(); i++) {
            birthList.get(i).setPercentage((Double.parseDouble(birthList.get(i).getValue()) / Double.parseDouble(total)) * 100 + "%");

        }
        System.out.println(birthList);
        birthChartEntity.setTotal(Integer.parseInt(total));
        birthChartEntity.setList(birthList);
        graphs.add(birthChartEntity);

        StatisticChartEntiey jobChartEntity  = new StatisticChartEntiey();
        List<StatisticItemEntity> jobList =userDao.statisticByJob(id);
        for (int i = 0; i < jobList.size(); i++) {
            jobList.get(i).setPercentage((Double.parseDouble(jobList.get(i).getValue()) / Double.parseDouble(total)) * 100 + "%");
        }
        System.out.println(jobList);
        jobChartEntity.setTotal(Integer.parseInt(total));
        jobChartEntity.setList(jobList);
        graphs.add(jobChartEntity);

        String[] ids = memberDao.selectUserIdsByGroupId(id);
        for (int i = 0; i < ids.length; i++) {
            StatisticUserEntity result = surveyFeignClient.getStatisticUserEntity(ids[i]);
            users.add(result);
        }



        statisticGroupEntry.setTotal(total);
        statisticGroupEntry.setOnline(online);
        statisticGroupEntry.setHeatmap(heatmap);
        statisticGroupEntry.setGraphs(graphs);
        statisticGroupEntry.setUsers(users);

        return statisticGroupEntry;
    }
}
