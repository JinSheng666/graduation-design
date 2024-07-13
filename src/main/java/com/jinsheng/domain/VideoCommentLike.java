package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 视频评论点赞
* @TableName video_comment_like
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VideoCommentLike implements Serializable {

    /**
    * 唯一标识
    */
    private Integer vclId;
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
