package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Popular;
import com.jinsheng.mapper.PopularMapper;
import com.jinsheng.service.PopularService;
import com.jinsheng.vo.VoForum;
import com.jinsheng.vo.VoPopular;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class PopularServiceImpl implements PopularService {

    @Autowired
    private PopularMapper popularMapper;

    /**
     * 获得所有热门榜单
     * */
    @Override
    public Result findPopular() {

        List<VoPopular> popular = popularMapper.findPopular();

        for (VoPopular forum: popular) {

            String pics = forum.getPics();
            String videoUrl = forum.getVideoUrl();

            if (pics == null){
                String[] array = new String[0];
                forum.setPicss(array);
            }else {
                forum.setPicss(pics.split(","));
            }
            if (videoUrl == null){
                String[] array = new String[0];
                forum.setVideoUrls(array);
            }else {
                forum.setVideoUrls(videoUrl.split(","));
            }

        }

        Integer code = !popular.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !popular.isEmpty() ? true:false;
        String msg = !popular.isEmpty() ? "榜单查询成功！":"榜单查询失败！";

        return Result.result(code,flag,msg,popular);
    }

    /**
     * 获得所有热门榜单
     * */
    @Override
    public Result popularAll() {

        List<VoForum> voForums = popularMapper.popularAll();

        Integer code = !voForums.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !voForums.isEmpty() ? true:false;
        String msg = !voForums.isEmpty() ? "榜单查询成功！":"榜单查询失败！";

        return Result.result(code,flag,msg,voForums);
    }

    @Override
    public Result deletePopularById(Integer popularId) {

        int i = popularMapper.deletePopular(popularId);

        Integer code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ? "榜单删除成功！":"榜单删除失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据id查询
     * */
    @Override
    public Result popularById(Integer popularId) {

        VoPopular voPopular = popularMapper.popularId(popularId);

        Integer code = voPopular != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = voPopular != null ? true:false;
        String msg = voPopular != null ? "榜单查询成功！":"榜单查询失败！";

        return Result.result(code,flag,msg,voPopular);
    }
}
