package com.jinsheng.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装表现层对象，主要用户页面显示
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoCurrentUser {

    private String userName;//用户名

    private String headSculpture;//用户头像

    private String signature;//个签


}
