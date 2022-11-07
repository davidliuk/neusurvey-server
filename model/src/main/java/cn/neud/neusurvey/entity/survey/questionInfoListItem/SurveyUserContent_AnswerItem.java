package cn.neud.neusurvey.entity.survey.questionInfoListItem;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("answer")
public class SurveyUserContent_AnswerItem {
    /*
    选项从属问卷id
     */
    private String surveyId;

    /*
    跳转到该问题的选项id
     */
    private String userId;

    /*
    跳转到该问题的选项id
     */
    private String content;

}
