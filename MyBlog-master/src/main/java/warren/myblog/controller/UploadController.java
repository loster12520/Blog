package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import warren.myblog.common.Result;
import warren.myblog.utils.QiniuUtils;

import java.util.UUID;

import static warren.myblog.Params.ErrorCode.FILE_UPLOAD_ERROR;

/*
 * author: Warren
 */
@RestController
@RequestMapping
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Operation(tags = "上传图片")
    @PostMapping("/user/upload")
    public Result upload(@RequestParam("image") MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(FILE_UPLOAD_ERROR.getCode(), FILE_UPLOAD_ERROR.getMsg());
    }

}