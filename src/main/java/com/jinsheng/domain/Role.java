package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 角色表
* @TableName tb_role
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {

    /**
    * 角色id
    */
    private Integer roleId;
    /**
    * 角色名
    */
    private String roleName;
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

}
