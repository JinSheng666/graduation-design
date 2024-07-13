package com.jinsheng.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.jinsheng.common.Code.CODE_OK;

/**
 * 响应结果封装类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {

    //成员属性
    private int code;//状态码

    private boolean flag;//成功响应为true,失败响应为false

    private String message;//响应信息

    private Object data;//响应数据

    private Integer totalNum;//分页总数

    public static Result result(int code,boolean flag,String message){
        return new Result(code,flag,message,null,null);
    }

    public static Result result(int code,boolean flag,String message,Object data){
        return new Result(code,flag,message,data,null);
    }

    public static Result page(int code,boolean flag,String message,Object data,Integer totalNum){
        return new Result(code,flag,message,data,totalNum);
    }

    //成功响应的方法 -- 返回的Result中只封装了成功状态码
    public static Result ok(){
        return new Result(CODE_OK,true,null, null,null);
    }
    //成功响应的方法 -- 返回的Result中封装了成功状态码和响应信息
    public static Result ok(String message){
        return new Result(CODE_OK,true,message, null,null);
    }
    //成功响应的方法 -- 返回的Result中封装了成功状态码和响应数据
    public static Result ok(Object data){
        return new Result(CODE_OK,true,null, data,null);
    }
    //成功响应的方法 -- 返回的Result中封装了成功状态码和响应信息和响应数据
    public static Result ok(String message, Object data){
        return new Result(CODE_OK,true,message, data,null);
    }
    //失败响应的方法 -- 返回的Result中封装了失败状态码和响应信息
    public static Result err(int errCode, String message){
        return new Result(errCode,false, message, null,null);
    }
    //失败响应的方法 -- 返回的Result中封装了失败状态码和响应信息和响应数据
    public static Result err(int errCode,String message,Object data){
        return new Result(errCode,false,message, data,null);
    }
}
