package cn.neud.neusurvey.entity.survey.questionInfoListItem;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("goto")
public class SurveyChoice_GotoItem {
    /*
    选项从属问卷id
     */
    private String surveyId;

    /*
    跳转到该问题的选项id
     */
    private String choiceId;

}
