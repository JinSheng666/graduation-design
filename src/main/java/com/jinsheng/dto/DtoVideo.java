package com.jinsheng.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DtoVideo {


    /**
     * 唯一标识
     */
    private Integer videoId;
    /**
     * 用户ID
     */
    private Integer userId;
    /*用户名*/
    private String userName;
    /*用户头像*/
    private String headSculpture;
    /**
     * 标题
     */
    private String title;
    /**
     * 视频路径
     */
    private String video;

    private List<String> videos;
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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;



}
