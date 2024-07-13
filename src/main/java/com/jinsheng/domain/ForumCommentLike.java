package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 论坛评论点赞
* @TableName forum_comment_like
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForumCommentLike implements Serializable {

    /**
    * 唯一标识
    */
    private Integer fclId;
    /**
    * 论坛ID
    */
    private Integer commentId;
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
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date createTime;
    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date updateTime;


}
