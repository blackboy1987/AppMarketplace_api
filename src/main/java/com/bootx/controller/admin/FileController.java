package com.bootx.controller.admin;

import com.bootx.common.CacheKey;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.Admin;
import com.bootx.entity.Material;
import com.bootx.security.CurrentUser;
import com.bootx.service.MaterialService;
import com.bootx.util.DateUtils;
import com.bootx.util.UploadUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author black
 */
@RestController("AdminFileController")
@RequestMapping("/api/admin/file")
public class FileController extends BaseController {

    @Resource
    private MaterialService materialService;

    @PostMapping("/upload")
    public Result save(MultipartFile file, @CurrentUser Admin admin) throws IOException {
        if(file==null){
            return Result.error("未上传文件");
        }
       if(admin==null){
           return Result.error("未登录");
       }
       String fileName = file.getOriginalFilename();
       File tmpFile = new File(SystemUtils.getJavaIoTmpDir(),fileName);
       String path = UUID.randomUUID().toString().replaceAll("-","")+"_"+fileName;
       path = "yysc/"+ DateUtils.formatDateToString(new Date(),"yyyy/MM/dd/")+path;
       file.transferTo(tmpFile);
        String upload = UploadUtils.upload(path, tmpFile);
        Material material = new Material();
        material.setAdminId(admin.getId());
        material.setUrl(upload);
        material.setName(fileName);
        material.setSize(file.getSize());
        materialService.save(material);

        return Result.success();
    }
}
