package com.jinsheng.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 此User类只封装了用户的用户id、用户名和真实姓名
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrentUser {

    private Integer userId;//用户id

    private String code;//用户编码

    private String userName;//用户名


}
