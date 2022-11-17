package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * answer
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("answer")
public class AnswerEntity {

    /**
     * 问卷id
     */
    private String id;

    /**
     * 问卷id
     */
	private String surveyId;
    /**
     * 问题id
     */
	private String questionId;
    /**
     * 用户id
     */
	private String userId;
    /**
     * 内容
     */
	private String content;

    @TableField(exist = false)
    private List<String> choices;
    /**
     * 创建人
     */
	private String creator;
    /**
     * 创建时间
     */
	private Date createDate;
    /**
     * 更新人
     */
	private String updater;
    /**
     * 更新时间
     */
	private Date updateDate;
    /**
     * 软删除
     */
	private String isDeleted;
    /**
     * 保留项json
     */
	private String reserved;
}