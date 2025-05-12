package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.exception.BadRequestException;
import com.spidey.openmusicapi.exception.GlobalException;
import com.spidey.openmusicapi.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload.path}")
    private String resourceUploadPath;

    /**
     * 上传文件
     * @param file 文件
     * @return URI路径
     */
    @PostMapping("upload")
    public ApiResponse<String> upload(MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();

        if (Objects.isNull(fileName)) {
            // 默认文件名
            fileName = "file.bin";
        } else {
            // 替换掉非法字符
            fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
        }

        Path uploadedPath = Path.of(
                resourceUploadPath, fileId, fileName);
        try {
            if (! uploadedPath.getParent().toFile().mkdirs()) {
                throw new GlobalException("文件夹创建失败");
            }
            file.transferTo(uploadedPath);
        } catch (Exception e) {
            throw new BadRequestException("上传失败：%s".formatted(e.getMessage()));
        }

        String url = Path
                .of("file", "download", fileId,
                        URLEncoder.encode(fileName, StandardCharsets.UTF_8))
                .toString()
                .replaceAll("\\\\", "/");

        return ApiResponse.success(url);
    }

    /**
     * 下载文件
     * @param request 请求
     * @param response 响应
     */
    @GetMapping("download/**")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI().substring(15);
        Path filePath = Path.of(
                resourceUploadPath,
                URLDecoder.decode(path, StandardCharsets.UTF_8));
        if (! filePath.toFile().exists()) {
            throw new NotFoundException("文件不存在");
        }

        try {
            response.setContentType(Files.probeContentType(filePath));
        } catch (IOException e) {
            response.setContentType("application/octet-stream");
        }

        try {
            Files.copy(filePath, response.getOutputStream());
        } catch (IOException e) {
            throw new BadRequestException("下载失败：%s".formatted(e.getMessage()));
        }
    }

}
