package com.ttg.service.park.intelligent.web;

import com.ttg.service.park.common.exception.R;
import com.ttg.service.park.common.utils.util.DateUtil;
import com.ttg.service.park.common.utils.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * <p>Title: FileController</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/11 15:40
 */
@Api("工具类接口")
@Controller
@RequestMapping(value = "/sys/util")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${file.upload.path}")
    private String uploadPath;

    @ApiOperation(value="文件上传接口",notes = "文件上传接口",httpMethod = "POST")
    @ResponseBody
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {

        //得到源文件名称
        String originFileName = file.getOriginalFilename();

        //判断文件内容是否为空
        if (file.isEmpty()) {
            logger.error("file is empty");
            return R.error(-1, "文件内容不能为空");
        }
        //调用文件上传工具方法
        String callBackPath = FileUtil.upload(file, uploadPath, originFileName);
        if (callBackPath != null) {
            return R.ok().put("data", callBackPath);
        } else {
            return R.error(-1, "文件上传失败！");
        }
    }

    @ApiOperation(value="文件显示接口",notes = "文件显示接口",httpMethod = "POST")
    @PostMapping(value = "/showimg")
    public void show(@RequestBody Map<String,String> map, HttpServletResponse response) {
        String filename = map.get("filename");
        if(filename == null){
            logger.error("filename is null");
            return;
        }
        response.setContentType("image/jpeg/jpg/png/gif/bmp/tiff/svg"); // 设置返回内容格式

        try {
            filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("file charset error:{}",e.getMessage());
            return;
        }

        File file = new File( uploadPath+File.separator + filename);

        try (InputStream in = new FileInputStream(file);   //用该文件创建一个输入流
             OutputStream os = response.getOutputStream()) {
            if (file.exists()) {   //如果文件存在
                byte[] b = new byte[1024];
                while (in.read(b) != -1) {
                    os.write(b);
                }
            }
        } catch (Exception e) {
            logger.error("file not exists");
        }
    }
}
