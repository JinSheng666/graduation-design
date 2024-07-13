package com.jinsheng.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
* 热门榜单
* @TableName tb_popular
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Popular implements Serializable {

    /**
    * 唯一标识
    */
    private Integer popularId;
    /**
    * 热门论坛ID
    */
    private Integer forumId;
    /**
    * 0正常，1删除
    */
    private String isDelete;

   /*———————————— 手动添加 ——————————*/
    //话题
    private String topics;
}
