package com.jinsheng;

import com.jinsheng.common.Page;
import com.jinsheng.mapper.ForumMapper;
import com.jinsheng.utils.VideoOrImageUtil;
import com.jinsheng.vo.VoForum;
import com.jinsheng.vo.VoForumComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class GraduationDesignApplicationTests {

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private VideoOrImageUtil videoOrImageUtil;

    @Value("${file.upload-img-path}")
    private String getImgPath;

    @Test
    void imgUploadText() throws IOException {
//        videoOrImageUtil.videoOrImgUpload();
        Resource resource = new ClassPathResource(getImgPath);

        File file1 = resource.getFile();

        System.out.println("磁盘文件位置："+file1.getAbsolutePath()+"\\"+"123.jpg");
    }

    @Test
    void contextLoads() {
        Page page = new Page();
        page.setPageSize(10);
        page.setPageNum(1);
        List<VoForum> voForums = forumMapper.forumSearch(null, page);

        for (VoForum forum: voForums) {

            if (forum.getVideoUrl() == null){
                break;
            }else {
                String[] split = forum.getVideoUrl().split(",");
                forum.setVideoUrls(split);
            }

            for (int i = 0; i < forum.getVideoUrls().length; i++) {
                System.out.println(forum.getVideoUrls()[i]);
            }
        }



        }

    }
