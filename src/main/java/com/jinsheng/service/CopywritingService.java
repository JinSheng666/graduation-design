package com.jinsheng.service;

import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Copywriting;

import java.util.List;

public interface CopywritingService {

    Result copyWritingPage(Copywriting copywriting, Page page);

    Result copyWriting();

    Result updateCopeWriting(Copywriting copywriting);

    Result addCopeWriting(Copywriting copywriting);

    Result deleteCopeWriting(List<Integer> cwIds);

    Result findCopyWritingByUserId(String token);

    Result getCopyWritingByUserId(Integer userId);

    Result deleteCopeWritingById(Integer id);

    Result copywritingById(Integer id);
}
