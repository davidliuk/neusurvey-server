package cn.neud.neusurvey.entity.survey;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * question
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("question")
public class QuestionEntity {

    /**
     * 主键id
     */
	private String id;
    /**
     * 题干
     */
	private String stem;
    /**
     * 问题类型
     */
	private Integer questionType;

    @TableField(exist = false)
    private List<ChoiceEntity> choices;

    /**
     * 备注json
     */
	private String note;
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