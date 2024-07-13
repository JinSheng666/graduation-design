package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 文案
* @TableName tb_copywriting
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Copywriting implements Serializable {

    /**
    * 唯一标识
    */
    private Integer copywritingId;
    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 文案文本
    */
    private String text;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 更新时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
    * 选择（0不选择，1选择）
    */
    private String isSelect;
    /**
    * 删除（0不删除，1删除）
    */
    private String isDelete;


    /*-------手动添加----------*/
    /*用户名*/
    private String userName;

    /*开始时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /*结束世间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
