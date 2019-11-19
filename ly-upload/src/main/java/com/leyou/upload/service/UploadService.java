package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: 上传文件业务实现
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/11/18
 */
@Slf4j
@Service
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties uploadProperties;

    /**
     * 上传图片
     * @param file
     * @return java.lang.String
     * @author vanguard
     * @date 19/11/18 21:09
     */
    public String uploadImage(MultipartFile file) {

        try {
            //校验文件类型
            String contentType = file.getContentType();
            if(!uploadProperties.getAllowTypes().contains(contentType)) {
                throw new LyException(ExceptionEnums.INVALID_FILE_TYPE);
            }

            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null) {
                throw new LyException(ExceptionEnums.INVALID_FILE_TYPE);
            }
            //获取文件的后缀
            String extension = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");

            //上传文件
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //返回路径
            return uploadProperties.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            //上传图片失败
            log.error("上传文件失败！", e);
            throw new LyException(ExceptionEnums.UPLOAD_FILE_ERROR);
        }

    }
}
