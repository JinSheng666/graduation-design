package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* 视频点赞
* @TableName video_like
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoLike implements Serializable {

    /**
    * 唯一标识
    */
    private Integer vlId;
    /**
    * 视频ID
    */
    private Integer videoId;
    /**
    * 点赞用户ID
    */
    private Integer likePostId;
    /**
    * 状态（默认为0，1为取消）
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
