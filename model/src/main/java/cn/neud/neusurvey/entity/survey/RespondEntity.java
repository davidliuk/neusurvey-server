package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-09
 */
@Data
@TableName("respond")
public class RespondEntity {

    /**
     * 答者id
     */
	private String userId;
    /**
     * 问卷id
     */
	private String surveyId;
    /**
     * 开始答卷时间
     */
	private Date startTime;
    /**
     * 结束答卷时间
     */
	private Date endTime;
    /**
     * 回答次数
     */
	private Date answerTime;
}