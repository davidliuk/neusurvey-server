package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * survey_type
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("survey_type")
public class SurveyTypeEntity {

    /**
     * 主键id
     */
	private String id;
    /**
     * 问卷类别名字
     */
	private String name;
    /**
     * 描述
     */
	private String description;
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