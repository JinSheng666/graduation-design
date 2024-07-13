package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
* 视频
* @TableName tb_video
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Video implements Serializable {

    /**
    * 唯一标识
    */
    private Integer videoId;
    /**
    * 用户ID
    */
    private Integer userId;
    /**
    * 标题
    */
    private String title;
    /**
    * 视频路径
    */
    private String video;
    /**
    * 文本内容
    */
    private String text;
    /**
    * 转发数量
    */
    private Integer attitudesCount;
    /**
    * 评论数量
    */
    private Integer commentsCount;
    /**
    * 点赞数量
    */
    private Integer likeCount;
    /**
    * 隐私（默认为0，1为隐私）
    */
    private String isPrivacy;
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
    * 删除（默认为0，1为删除）
    */
    private String isDelete;

    /*______ 手动添加 ______*/

    private String userName;

    //返回前端时,自动将Date转换成指定格式的json字符串
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    //返回前端时,自动将Date转换成指定格式的json字符串
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
