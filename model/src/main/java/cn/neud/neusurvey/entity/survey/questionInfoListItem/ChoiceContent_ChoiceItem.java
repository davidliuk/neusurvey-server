package cn.neud.neusurvey.entity.survey.questionInfoListItem;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("choice")
public class ChoiceContent_ChoiceItem {
    /*
    选项从属问卷id
     */
    private String id;

    /*
    跳转到该问题的选项id
     */
    private String content;

}
