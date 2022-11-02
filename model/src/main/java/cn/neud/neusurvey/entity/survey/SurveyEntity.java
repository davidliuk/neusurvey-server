package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * survey
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("survey")
public class SurveyEntity {

    /**
     * 主键id
     */
	private String id;
    /**
     * 问卷名字
     */
	private String name;
    /**
     * 管理人id
     */
	private String managedBy;
    /**
     * 描述
     */
	private String description;
    /**
     * 问卷开始时间
     */
	private Date startTime;
    /**
     * 问卷结束时间
     */
	private Date endTime;
    /**
     * 答卷次数限制
     */
	private Integer limit;
    /**
     * 问卷类型
     */
	private String typeId;

    @TableField(exist = false)
    private List<QuestionEntity> questions;

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