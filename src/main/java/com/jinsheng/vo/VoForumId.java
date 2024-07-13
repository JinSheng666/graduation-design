package com.jinsheng.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoForumId {

    /**
     * 论坛唯一标识
     */
    private Integer forumId;

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

    private List<String> picss;

    private List<String> videos;



}
