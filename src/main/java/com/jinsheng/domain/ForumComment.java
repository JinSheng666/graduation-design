package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 论坛评论区
* @TableName forum_comment
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ForumComment implements Serializable {

    /**
    * 唯一标识
    */
    private Integer fcId;
    /**
    * 论坛ID
    */
    private Integer forumId;
    /**
    * 用户ID
    */
    private Integer userId;
    /**
    * 父ID（默认为0）
    */
    private Integer parentId;
    /**
    * 评论内容
    */
    private String comment;
    /**
    * 点赞数
    */
    private Integer likeCount;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 是否删除（默认为0，1为删除）
    */
    private String isDelete;


    /*_______ 手动添加 _______*/
    private String userName;


}
