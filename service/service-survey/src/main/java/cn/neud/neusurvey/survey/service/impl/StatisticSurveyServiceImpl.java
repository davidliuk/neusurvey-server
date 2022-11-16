package cn.neud.neusurvey.survey.service.impl;

import cn.neud.common.utils.Result;
import cn.neud.neusurvey.entity.statistics.*;
import cn.neud.neusurvey.entity.survey.ChoiceEntity;
import cn.neud.neusurvey.entity.survey.HaveEntity;
import cn.neud.neusurvey.entity.survey.QuestionEntity;
import cn.neud.neusurvey.entity.survey.SurveyEntity;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.ChoiceContent_ChoiceItem;
import cn.neud.neusurvey.entity.user.UserEntity;
import cn.neud.neusurvey.survey.dao.*;
import cn.neud.neusurvey.survey.service.HaveService;
import cn.neud.neusurvey.survey.service.StatisticQuestionService;
import cn.neud.neusurvey.survey.service.StatisticSurveyService;
import cn.neud.neusurvey.user.client.UserFeignClient;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticSurveyServiceImpl implements StatisticSurveyService {

    @Resource
    UserFeignClient userFeignClient;

    @Autowired
    SurveyDao surveyDao;

    @Autowired
    AnswerDao answerDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    ChoiceDao choiceDao;

    @Autowired
    ChooseDao chooseDao;

    @Autowired
    HaveDao haveDao;

    //雷世鹏：该函数比较长，因此该部分使用嵌入式编程，如需调试或查看，请先全部折叠，再部分展开
    @Override
    public Result surveyStatistic(String id) {

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
            //select count(distinct user_id) from answer where survey_id=#{surveyId}
            questionnaireTotal=answerDao.getQuestionnaireTotal(id);
        }
        res.setQuestionnaireTotal(questionnaireTotal);

        //为啥要把这个往回传呀
        res.setQuestionnaireId(id);

        //问卷名称
        res.setName(surveyEntity.getName());

        //问卷描述
        res.setDescription(surveyEntity.getDescription());

        //问卷创建者
        res.setManagedBy(surveyEntity.getManagedBy());

        //地理热图
        StatisticHeatMapEntity_WJC heatMap=new StatisticHeatMapEntity_WJC();
        {
            // 注意: 省份的名字得有"省"，直辖市要有"市“
            //生成data项
            List<StatisticItemEntity_WJC> statisticItemEntities=new ArrayList<>();
            {
                //思路，找到那些回答的那些userid，然后得到他们的省份信息，分组求和一下就行了
                List<String> userIdAnswered=answerDao.getuserIdBySurveyId(id);
                List<String> provinces=userFeignClient.provinceStatistic(userIdAnswered);

                //手动统计,跨表sql调用起来比较麻烦
                for(int i=0;i< provinces.size();i++)
                {
                    String province = provinces.get(i);

                    //找得到就val++，找不到就新建一个
                    boolean canFind=false;
                    for(int j=0;j<statisticItemEntities.size();j++)
                    {
                        if(statisticItemEntities.get(j).getName().equals(province))
                        {
                            Integer val=statisticItemEntities.get(j).getValue()+1;
                            statisticItemEntities.get(j).setValue(val);
                            canFind=true;
                            break;
                        }
                    }

                    //找不到就新建
                    if(!canFind)
                    {
                        StatisticItemEntity_WJC tempStasticEntity=new StatisticItemEntity_WJC();
                        tempStasticEntity.setName(province);
                        tempStasticEntity.setValue(1);

                        statisticItemEntities.add(tempStasticEntity);
                    }
                }
            }
            heatMap.setData(statisticItemEntities);

        }
        res.setHeatmap(heatMap);

        //问题情况
        //select * from have where survey_id=#{surveyId}
        List<HaveEntity> haveEntities=haveDao.getQuestionBySurveyId(id);
        List<StatisticQuestionEntity_WJC> questions=new ArrayList<>();
        if(haveEntities!=null)
        {
            for(int i=0;i<haveEntities.size();i++)
                questions.add(QuestionStatistic(haveEntities.get(i).getQuestionId(),id));
        }
        res.setQuestions(questions);
        
        return result.ok(res);
    }


    public StatisticQuestionEntity_WJC QuestionStatistic(String questionId,String surveyId) {

        //存在性检查
        QuestionEntity questionEntity=questionDao.selectById(questionId);
        if(questionEntity==null)
            return null;
        if(questionEntity.getIsDeleted()!=null)
            if(questionEntity.getIsDeleted()==1)
                return null;

        //构建数据容器
        StatisticQuestionEntity_WJC res=new StatisticQuestionEntity_WJC();
        res.setChartType("bar");

        res.setQuestionType(questionEntity.getQuestionType());

        res.setStem(questionEntity.getStem());

        //获取回答问题的总人数
        Integer questionnaireTotal=0;
        {
            //思路：在answer表中按照问卷id和问题id直接count出该问卷回答总人数，注意是问卷
            //select count user_id from answer where survey_id=#{surveyId} AND question_id=#{questionId}
            questionnaireTotal=answerDao.getQuestionTotal(surveyId,questionId);
        }
        res.setTotal(questionnaireTotal);

        //为啥要把这个往回传呀
        res.setId(questionId);

        //问题情况
        List<ChoiceContent_ChoiceItem> choiceEntities=choiceDao.getChoiceContentByQuestionId(questionEntity.getId());
        List<StatisticChoicesEntity_WJC> choices=new ArrayList<>();
        if(choiceEntities!=null)
        {
            for(int i=0;i<choiceEntities.size();i++)
            {
                StatisticChoicesEntity_WJC statisticChoicesEntity=new StatisticChoicesEntity_WJC();
                //获取内容
                statisticChoicesEntity.setContent(choiceEntities.get(i).getContent());
                //获取填写该问题的该答案的人数
                //思路：在choose表中按照问卷id和问题id和选项id直接count出该问卷回答总人数
                //select count user_id from choose where survey_id=#{surveyId} AND question_id=#{questionId} AND choice_id=#{choiceId}
                Integer choiceTotal=chooseDao.getChoiceTotal(surveyId,questionId,choiceEntities.get(i).getId());
                statisticChoicesEntity.setChoiceQuantity(choiceTotal);
                choices.add(statisticChoicesEntity);
            }
        }
        res.setChoices(choices);

        return res;
    }
}
