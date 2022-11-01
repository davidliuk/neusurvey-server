package cn.neud.neusurvey.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * user
 *
 * @author David l729641074@163.com
 * @since 1.0.0 2022-10-29
 */
@Data
@TableName("user")
public class UserLoginEntity {

    /**
     * 用户名
     */
	private String username;
    /**
     * 密码
     */
	private String password;
    
}