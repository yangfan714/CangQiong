package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "文件上传")
@Slf4j
public class CommonController {
    @Value("${sky.upload.path}")  // 从配置文件读取上传路径
    private String uploadPath;

    @Value("${sky.upload.url}")   // 从配置文件读取访问URL
    private String uploadUrl;

    @PostMapping("/upload")
    @ResponseBody
    public Result<String> uploadfile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        // 确保目录存在
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return Result.error("文件名无效");
        }
        // 获取文件后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!extension.equalsIgnoreCase(".png")
                && !extension.equalsIgnoreCase(".jpg")
                && !extension.equalsIgnoreCase(".jpeg")) {
            return Result.error("文件格式不支持");
        }
        System.out.println("传入的图片文件名为："+originalFilename);
        // 生成新文件名
        String fileName = UUID.randomUUID().toString() + extension;
        // 保存文件
        System.out.println("新的UUID文件名："+fileName);
        File dest = new File(uploadPath + fileName);
        if (dest.exists()) {
            System.out.println("图片保存成功:"+dest.getAbsolutePath());
        }
        try {
            file.transferTo(dest);
            log.info("文件保存成功: {}", dest.getAbsolutePath());
            log.info("文件是否存在: {}", dest.exists());
            log.info("文件大小: {} bytes", dest.length());
        } catch (IOException e) {
            log.error("文件保存失败", e);
            return Result.error("文件上传失败");
        }

        String imageUrl = uploadUrl + "/upload/" + fileName;
        System.out.println("图片访问URL："+ imageUrl);

        // 返回文件访问路径（注意这里返回的路径格式）
        return Result.success(imageUrl);
    }
}