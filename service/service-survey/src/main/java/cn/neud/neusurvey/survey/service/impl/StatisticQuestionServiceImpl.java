package cn.neud.neusurvey.survey.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.*;

import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.survey.dao.AnswerDao;
import cn.neud.neusurvey.survey.dao.SurveyDao;
import cn.neud.neusurvey.survey.service.StatisticQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticQuestionServiceImpl implements StatisticQuestionService {

    @Autowired
    SurveyDao surveyDao;

    @Autowired
    AnswerDao answerDao;

    //雷世鹏：该函数比较长，因此该部分使用嵌入式编程，如需调试或查看，请先全部折叠，再部分展开
    @Override
    public Result questionStatistic(String id) {

        Result<StatisticSurveyEntity> result=new Result<>();

        //存在性检查
        SurveyEntity surveyEntity=surveyDao.selectById(id);
        if(surveyEntity==null)
            return result.error("找不到该问卷");
        if(surveyEntity.getIsDeleted()!=null
            &&surveyEntity.getIsDeleted().equals("1"))
            return result.error("该问卷已经删除");

        //构建数据容器
        StatisticSurveyEntity res=new StatisticSurveyEntity();

        //获取回答问卷的总人数
        Integer questionnaireTotal;
        {
            //思路：在answer表中按照问卷id直接count出该问卷回答总人数，注意是问卷
            // select count(distinct user_id) from answer where survey_id=#{id}
            //questionnaireTotal=answerDao.getQuestionnaireTotal();
        }
        res.setQuestionnaireTotal(1);

        //为啥要把这个往回传呀
        res.setQuestionnaireId(id);

        //问卷名称
        res.setName(surveyEntity.getName());

        //问卷描述
        res.setDescription(surveyEntity.getDescription());

        //问卷创建者
        res.setManagedBy(surveyEntity.getManagedBy());

        //todo 联系佳驰
        //地理热图
        StatisticHeatMapEntity_WJC heatMap=new StatisticHeatMapEntity_WJC();
        {

        }
        res.setHeatmap(heatMap);

        //问题情况
        List<StatisticQuestionEntity_WJC> questions=new ArrayList<>();
        {

        }
        res.setQuestions(questions);
        
        return null;
    }
}
