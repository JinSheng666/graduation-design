package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 视频评论
* @TableName video_comment
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VideoComment implements Serializable {

    /**
    * 唯一标识
    */
    private Integer vcId;
    /**
    * 视频ID
    */
    private Integer videoId;
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
    * 点赞数量
    */
    private Integer likeCount;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-mm-dd hh-mm-ss")
    private Date createTime;
    /**
    * 删除（默认为0，1为删除）
    */
    private String isDelete;

    private String userName;


}
