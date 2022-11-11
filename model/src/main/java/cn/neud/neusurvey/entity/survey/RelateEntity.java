package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-11-11
 */
@Data
@TableName("relate")
public class RelateEntity {

    /**
     * 
     */
	private String surveyId;
    /**
     * 
     */
	private String groupId;
    /**
     * 
     */
	private Integer isDeleted;
}