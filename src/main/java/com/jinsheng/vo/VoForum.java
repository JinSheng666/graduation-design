package com.jinsheng.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoForum {

    /**
     * 论坛唯一标识
     */
    private Integer forumId;

    /*用户编码*/
    private String userId;

    /*用户名*/
    private String userName;

    /*用户头像*/
    private String headSculpture;

    /*所在地*/
    private String location;

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

    private String[] picss;

    /**
     * 视频
     */
    private String videoUrl;

    private String[] videoUrls;

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
     * 创建时间
     */
    //返回前端时,自动将Date转换成指定格式的json字符串
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;





}
