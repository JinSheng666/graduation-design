package com.jinsheng.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 论坛点赞
* @TableName forum_like
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForumLike implements Serializable {

    /**
    * 唯一标识
    */
    private Integer flId;
    /**
    * 论坛ID
    */
    private Integer forumId;
    /**
    * 点赞的用户ID
    */
    private Integer likePostId;
    /**
    * 状态（1为取消，0为点赞）
    */
    private String status;
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
