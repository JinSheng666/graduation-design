package com.jinsheng.vo;

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
public class VoVideoComment {

    /**
     * 唯一标识
     */
    private Integer vcId;
    /**
     * 视频ID
     */
    private Integer videoId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String userName;

    private String headSculpture;

    //追加的List<VoForumComment>集合属性 -- 用于存储当前评论的子评论
    private List<VoVideoComment> childComment;



}
