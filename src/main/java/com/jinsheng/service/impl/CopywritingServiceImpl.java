package com.jinsheng.service.impl;

import com.jinsheng.common.Code;
import com.jinsheng.common.CurrentUser;
import com.jinsheng.common.Page;
import com.jinsheng.common.Result;
import com.jinsheng.domain.Copywriting;
import com.jinsheng.mapper.CopywritingMapper;
import com.jinsheng.service.CopywritingService;
import com.jinsheng.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "com.jinsheng.service.impl.CopywritingServiceImpl")
public class CopywritingServiceImpl implements CopywritingService {

    @Autowired
    private CopywritingMapper cwMapper;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 分页查询
     * */
    @Override
    public Result copyWritingPage(Copywriting copywriting, Page page) {

        int count = cwMapper.cwCount(copywriting);
        page.setTotalNum(count);

        List<Copywriting> cws = cwMapper.copyWritingPage(copywriting, page);
        page.setResultList(cws);

        int code = !cws.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !cws.isEmpty() ? true:false;
        String msg = !cws.isEmpty() ?"文案分页查询成功！":"文案分页查询失败！";

        return Result.page(code,flag,msg,cws,page.getTotalNum());
    }

    /**
     * isSelect = 1 的文案
     * */
    @Override
//    @Cacheable(key = "'all:CW'")
    public Result copyWriting() {

        List<Copywriting> cwByIsSelect = cwMapper.findCwByIsSelect();

        int code = !cwByIsSelect.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !cwByIsSelect.isEmpty() ? true:false;
        String msg = !cwByIsSelect.isEmpty() ?"文案查询成功！":"文案查询失败！";

        return Result.result(code,flag,msg,cwByIsSelect);
    }

    /**
     * 修改文案
     * */
    @Override
    public Result updateCopeWriting(Copywriting copywriting) {

        int i = cwMapper.updateCopeWriting(copywriting);

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"文案修改成功！":"文案修改失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 文案新增
     * */
    @Override
    public Result addCopeWriting(Copywriting copywriting) {

        int i = cwMapper.addCopeWriting(copywriting);
        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"文案添加成功！":"文案添加失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 批量删除文案
     * */
    @Override
    public Result deleteCopeWriting(List<Integer> cwIds) {

        int i = cwMapper.deleteCopeWriting(cwIds);
        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"文案删除成功！":"文案删除失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据用户ID查询文案
     * */
    @Override
    public Result findCopyWritingByUserId(String token) {

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        Integer userId = currentUser.getUserId();

        List<Copywriting> copyWritingByUserId = cwMapper.findCopyWritingByUserId(userId);

        int count = cwMapper.findCopyWritingCountByUserId(userId);

        int code = !copyWritingByUserId.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !copyWritingByUserId.isEmpty() ? true:false;
        String msg = !copyWritingByUserId.isEmpty() ?"文案查询成功！":"文案查询失败！";

        return Result.page(code,flag,msg,copyWritingByUserId,count);
    }

    /**
     * 根据用户id查询文案
     * */
    @Override
    public Result getCopyWritingByUserId(Integer userId) {

        List<Copywriting> copyWritingByUserId = cwMapper.findCopyWritingByUserId(userId);

        int count = cwMapper.findCopyWritingCountByUserId(userId);

        int code = !copyWritingByUserId.isEmpty() ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = !copyWritingByUserId.isEmpty() ? true:false;
        String msg = !copyWritingByUserId.isEmpty() ?"文案查询成功！":"文案查询失败！";

        return Result.page(code,flag,msg,copyWritingByUserId,count);
    }

    @Override
    public Result deleteCopeWritingById(Integer id) {

        int i = cwMapper.deleteCopeWritingById(id);

        int code = i>0 ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = i>0 ? true:false;
        String msg = i>0 ?"文案删除成功！":"文案删除失败！";

        return Result.result(code,flag,msg);
    }

    /**
     * 根据文案id查询
     * */
    @Override
    public Result copywritingById(Integer id) {

        Copywriting copywriting = cwMapper.copywritingById(id);

        int code = copywriting != null ? Code.CODE_OK:Code.CODE_ERR_BUSINESS;
        Boolean flag = copywriting != null ? true:false;
        String msg = copywriting != null ?"文案删除成功！":"文案删除失败！";

        return Result.result(code,flag,msg,copywriting);
    }
}
