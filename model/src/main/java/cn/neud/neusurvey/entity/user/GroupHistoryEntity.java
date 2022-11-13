package cn.neud.neusurvey.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * group_history
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("group_history")
public class GroupHistoryEntity {

    /**
     * 主键id
     */
	private String id;
    /**
     * 群组id
     */
	private String groupId;
	/**
     * 群组名字
     */
	private String groupname;
	/**
     * 描述
     */
	private String description;
	/**
     * 头像
     */
	private String avatar;

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