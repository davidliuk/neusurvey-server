package cn.neud.neusurvey.entity.survey.questionInfoListItem;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("have")
public class SurveyNext_HaveItem {
    /*
    拥有该问题的问卷id
     */
    private String surveyId;

    /*
    下一个问题id
     */
    private String nextId;
}
