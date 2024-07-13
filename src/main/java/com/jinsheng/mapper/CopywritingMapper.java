package com.jinsheng.mapper;

import com.jinsheng.common.Page;
import com.jinsheng.domain.Copywriting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CopywritingMapper {

    int cwCount(@Param("cw") Copywriting cw);

    List<Copywriting> copyWritingPage(@Param("cw") Copywriting cw,@Param("page") Page page);

    List<Copywriting> findCwByIsSelect();

    int updateCopeWriting(Copywriting copywriting);

    int addCopeWriting(Copywriting copywriting);

    int deleteCopeWriting(List<Integer> cwIds);

    List<Copywriting> findCopyWritingByUserId(Integer userId);

    int findCopyWritingCountByUserId(Integer userId);

    int deleteCopeWritingById(Integer id);

    Copywriting copywritingById(Integer id);
}
