package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Title: 用户实体类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/06
 */
@Data
@Table(name = "tb_user")
public class User {

    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;

    /**
     * 用户名
     */
    @Length(min = 4, max = 30, message = "用户名只能在3~30位之间")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @Length(min = 4, max = 30, message = "密码只能在4~30位之间")
    private String password;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 密码的盐值
     */
    @JsonIgnore
    private String salt;
}
