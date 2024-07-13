package com.jinsheng.mapper;

import com.jinsheng.domain.Popular;
import com.jinsheng.vo.VoForum;
import com.jinsheng.vo.VoPopular;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PopularMapper {


    List<VoPopular> findPopular();

    List<VoForum> popularAll();
    //添加
    int addPopular(Integer forumId);
    //删除
    int deletePopular(Integer popularId);

    VoPopular popularId(Integer popularId);
    //删除
    int popularDelByForumId(Integer forumId);
}
