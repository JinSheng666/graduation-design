package com.jinsheng.utils;

import com.jinsheng.common.Code;
import com.jinsheng.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频与图片的响应工具类
 * */

@Component
@Slf4j
public class VideoOrImageUtil {

    @Value("${file.upload-video-path}")
    private String getVideoPath;

    @Value("${file.upload-img-path}")
    private String getImgPath;

    //初始化存储地址
    public void init() {
        try {
            Files.createDirectories(Paths.get(getVideoPath));
            Files.createDirectories(Paths.get(getImgPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    //读取输入流中的所有字节
    public byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /*响应视频*/
    public void getVideo(HttpServletRequest request, HttpServletResponse response,String videoName) throws IOException {
        response.reset();

        // 获取视频存储位置
        Path videoPath = Paths.get(getVideoPath).resolve(videoName);
        // 获取视频资源
        Resource resource = new UrlResource(videoPath.toUri());

        if (!resource.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 获取资源输入流
        InputStream inputStream = resource.getInputStream();

        // 获取资源文件长度
        long fileLength = resource.contentLength();
        System.out.println("视频总字节：" + fileLength);

        // 解析 Range 头部信息
        long rangeStart = 0;
        long rangeEnd = fileLength - 1;
        String rangeString = request.getHeader("Range");

        if (rangeString != null && rangeString.startsWith("bytes=")) {
            Pattern pattern = Pattern.compile("bytes=(\\d+)-(\\d*)");
            Matcher matcher = pattern.matcher(rangeString);
            if (matcher.matches()) {
                rangeStart = Long.parseLong(matcher.group(1));
                String rangeEndString = matcher.group(2);
                if (rangeEndString != null && !rangeEndString.isEmpty()) {
                    rangeEnd = Long.parseLong(rangeEndString);
                }
            }
        }

        long contentLength = rangeEnd - rangeStart + 1;
        response.setContentType("video/mp4");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 定位文件指针到指定位置
        inputStream.skip(rangeStart);

        // 获取响应输出流
        OutputStream outputStream = response.getOutputStream();

        // 每次请求只返回1MB的视频流
        byte[] buffer = new byte[1024 * 1024];
        long bytesToRead = contentLength;
        int bytesRead;
        while (bytesToRead > 0 && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, bytesToRead))) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            bytesToRead -= bytesRead;
        }

        // 关闭资源
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /*响应图片*/
    public ResponseEntity<byte[]> getImage(String imageName) throws IOException {
        // 获取图片资源
        byte[] imageBytes = Files.readAllBytes(Paths.get(getImgPath + "/" + imageName));

        // 获取文件扩展名
        String fileExtension = imageName.substring(imageName.lastIndexOf('.') + 1);

        // 根据文件扩展名设置对应的MediaType
        MediaType mediaType;
        switch (fileExtension.toLowerCase()) {

            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "jpg":
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            default:
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        // 构建响应
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageBytes);


    }

    /*视频图片上传 multiple*/
    public String videoOrImgUpload(MultipartFile file,String uploadDir){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Invalid file path: " + fileName);
            }
            Path targetLocation = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
        }

    }

    /*视频图片删除*/
    public Result videoOrImgDelete(String name) throws IOException {
        // 获取文件扩展名
        String fileExtension = name.substring(name.lastIndexOf('.') + 1);
        try {
            // 根据文件扩展名删除文件
            switch (fileExtension.toLowerCase()) {
                case "mp4":
                case "ogg":
                case "webm":
                    //拿到上传文件保存的磁盘文件的路径
                    String delFileVideoPath = getVideoPath + "/" + name;
                    log.debug("deleteFilePath"+delFileVideoPath);
                    //删除文件
                    Path pathVideo = Paths.get(delFileVideoPath);
                    Files.delete(pathVideo);
                    break;
                case "jpg":
                case "jpeg":
                case "png":
                case "gif":
                    //拿到上传文件保存的磁盘文件的路径
                    String delFileImgPath = getImgPath + "/" + name;
                    log.debug("deleteFilePath"+delFileImgPath);
                    Path pathImg = Paths.get(delFileImgPath);
                    Files.delete(pathImg);
                    break;
            }
            //删除成功
            return Result.ok("文件删除成功！");

        } catch (IOException e) {
            //上传失败
            return Result.err(Code.CODE_ERR_BUSINESS,"文件删除失败");
        }
    }
}
