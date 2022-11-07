package cn.neud.neusurvey.entity.survey;

import cn.neud.neusurvey.entity.survey.questionInfoListItem.ChoiceContent_ChoiceItem;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyChoice_GotoItem;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyNext_HaveItem;
import cn.neud.neusurvey.entity.survey.questionInfoListItem.SurveyUserContent_AnswerItem;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("question")
public class QuestionInfoEntity {

    /**
     * 主键id
     */
    @TableId
	private String id;
    /**
     * 题干
     */
	private String stem;
    /**
     * 问题类型
     */
	private Integer questionType;
    /**
     * 备注
     */
    private String note;

    /**
     * have表中的信息, 以question_id搜索
     * 表示在该问卷下,问题的直接跳转id是谁
     */
    private List<SurveyNext_HaveItem> haveItems;

    /**
     * goto表中的信息, 以question_id搜索
     * 表示在该问卷下,此问题由哪些选项跳转而来
     */
    private List<SurveyChoice_GotoItem> gotoItems;

    /**
     * choice表中的信息, 以belong_to搜索
     * 表示该问题有啥选项
     */
    private List<ChoiceContent_ChoiceItem> choiceItems;

    /**
     * answer表中的信息, 以question_id搜索
     * 表示该问卷中用户的回答内容
     */
    private List<SurveyUserContent_AnswerItem> answerItems;

    /**
     * 保留项json
     */
	private String reserved;
}