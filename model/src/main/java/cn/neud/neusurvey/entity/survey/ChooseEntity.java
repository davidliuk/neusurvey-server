package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-05
 */
@Data
@TableName("choose")
public class ChooseEntity {

    /**
     * 
     */
	private String surveyId;
    /**
     * 
     */
	private String userId;
    /**
     * 
     */
	private String choiceId;
    /**
     * 
     */
	private Integer isDeleted;
}