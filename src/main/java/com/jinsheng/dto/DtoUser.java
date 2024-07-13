package com.jinsheng.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专门接收视图传过来的数据进行封装
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {

    private String userName;

    private String code;

    private String password;

    private String headSculpture;

}
