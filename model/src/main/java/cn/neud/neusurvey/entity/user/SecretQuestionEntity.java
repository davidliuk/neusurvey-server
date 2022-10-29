package cn.neud.neusurvey.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * secret_question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("secret_question")
public class SecretQuestionEntity {

    /**
     * 主键id
     */
	private String id;
    /**
     * 题干
     */
	private String stem;
    /**
     * 答案
     */
	private String answer;
    /**
     * 用户id
     */
	private String userId;
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