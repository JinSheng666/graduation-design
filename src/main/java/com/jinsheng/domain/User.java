package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 用户表
* @TableName tb_user
*/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 用户编码
    */
    private String code;

    /**
    * 用户密码
    */
    private String password;

    /**
    * 头像
    */
    private String headSculpture;

    /**
    * 性别（0为女性，1为男性）
    */
    private String gender;

    /**
    * 年龄
    */
    private Integer age;

    /**
    * 用户类型（默认为1）
    */
    private String type;

    /**
    * 用户状态（默认为0）
    */
    private String status;

    /**
    * 个性签名
    */
    private String signature;

    /**
    * 所在地
    */
    private String location;

    /**
    * 家乡
    */
    private String home;

    /**
    * 描述
    */
    private String description;

    /**
    * 创建时间
    */
    //返回前端时,自动将Date转换成指定格式的json字符串
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
    * 更新时间
    */
    //返回前端时,自动将Date转换成指定格式的json字符串
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
    * 注销（默认为0，1为注销）
    */
    private String isDelete;


    /*------------- 手动添加 --------------*/

    private String roleName;

    private Integer intType;

}
