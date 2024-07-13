package com.jinsheng.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUser {

    private String userName;//用户名

    private String password;//密码

    private String status;//用户状态

    private String verificationCode;//验证码

}
