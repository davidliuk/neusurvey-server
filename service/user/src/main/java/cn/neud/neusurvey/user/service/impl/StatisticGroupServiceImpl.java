package cn.neud.neusurvey.user.service.impl;

import cn.neud.neusurvey.entity.statistics.StatisticChartEntity;
import cn.neud.neusurvey.entity.statistics.StatisticGroupEntry;
import cn.neud.neusurvey.entity.statistics.StatisticItemEntity;
import cn.neud.neusurvey.entity.statistics.StatisticUserEntity;
import cn.neud.neusurvey.user.dao.UserDao;
import cn.neud.neusurvey.user.dao.UserGroupDao;
import cn.neud.neusurvey.user.service.StatisticGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticGroupServiceImpl implements StatisticGroupService {

    @Autowired
    UserGroupDao userGroupDao;

    @Autowired
    UserDao userDao;





    @Override
    public StatisticGroupEntry groupStatistic(String id) {


        String total = String.valueOf(userDao.countGroupUser(id,""));
        String online = "99";
        StatisticGroupEntry statisticGroupEntry = new StatisticGroupEntry();
        StatisticChartEntity heatmap = new StatisticChartEntity();
        List<StatisticChartEntity> graphs = new ArrayList<>();
        StatisticUserEntity users = new StatisticUserEntity();

        List<StatisticItemEntity> heatmapList =  userDao.statisticHeatmap(id);;
        for (int i = 0; i < heatmapList.size(); i++) {
            heatmapList.get(i).setPercentage((Double.parseDouble(heatmapList.get(i).getValue()) / Double.parseDouble(total)) * 100 + "%");
        }
        System.out.println(heatmapList);
        heatmap.setTotal(total);
        heatmap.setItems(heatmapList);

        StatisticChartEntity genderChartEntity  = new StatisticChartEntity();
        List<StatisticItemEntity> genderList =userDao.statisticByGender(id);
        for (int i = 0; i < genderList.size(); i++) {
            genderList.get(i).setPercentage((Double.parseDouble(genderList.get(i).getValue()) / Double.parseDouble(total)) *100 + "%");
        }
        System.out.println(genderList);
        genderChartEntity.setTotal(total);
        genderChartEntity.setItems(genderList);
        graphs.add(genderChartEntity);

        StatisticChartEntity birthChartEntity  = new StatisticChartEntity();
        List<StatisticItemEntity> birthList =userDao.statisticByBirth(id);
        for (int i = 0; i < birthList.size(); i++) {
            birthList.get(i).setPercentage((Double.parseDouble(birthList.get(i).getValue()) / Double.parseDouble(total)) * 100 + "%");

        }
        System.out.println(birthList);
        birthChartEntity.setTotal(total);
        birthChartEntity.setItems(birthList);
        graphs.add(birthChartEntity);

        StatisticChartEntity jobChartEntity  = new StatisticChartEntity();
        List<StatisticItemEntity> jobList =userDao.statisticByJob(id);
        for (int i = 0; i < jobList.size(); i++) {
            jobList.get(i).setPercentage((Double.parseDouble(jobList.get(i).getValue()) / Double.parseDouble(total)) * 100 + "%");
        }
        System.out.println(jobList);
        jobChartEntity.setTotal(total);
        jobChartEntity.setItems(jobList);
        graphs.add(jobChartEntity);




        statisticGroupEntry.setTotal(total);
        statisticGroupEntry.setOnline(online);
        statisticGroupEntry.setHeatmap(heatmap);
        statisticGroupEntry.setGraphs(graphs);
        statisticGroupEntry.setUsers(users);

        return statisticGroupEntry;
    }
}
