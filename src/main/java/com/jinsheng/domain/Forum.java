package com.jinsheng.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* 论坛
* @TableName tb_forum
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Forum implements Serializable {

    /**
    * 论坛唯一标识
    */
    private Integer forumId;
    /**
    * 用户ID
    */
    private Integer userId;

    /*话题*/
    private String topics;
    /**
    * 文本内容
    */
    private String text;
    /**
    * 图片
    */
    private String pics;

    /**
    * 视频
    */
    private String videoUrl;
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
    * 是否隐私（默认为0，1为隐私）
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
    * 是否热门（默认为0，1为热门）
    */
    private String isPopular;
    /**
    * 是否删除（默认为0，1为删除）
    */
    private String isDelete;

    /*------------手动添加-------------*/

    private String userName;

    private Date startTime;

    private Date endTime;

    private List<String> picss;

    private List<String> videos;


}
