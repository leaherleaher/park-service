package com.ttg.service.park.common.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * <p>Title: FileUtil</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/11 15:44
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    
    /**
     * @Description //TODO 将文件名称重命名为UUID生成的文件
     * @Param [oldFileName]
     * @return java.lang.String
     **/
    public static String getFileName(String oldFileName){
        return getUUID()+ getSuffix(oldFileName);
    }

    //获得文件名称后缀
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //生成uuid
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @Description //TODO 文件上传方法
     * @Param [file, path, fileName]
     * @return boolean
     **/
    public static String upload(MultipartFile file,String path,String fileName){

        String callBackPath = DateUtil.format(new Date())+ File.separator + getFileName(fileName);
        //生成新的文件名  配置物理绝对路径/日期/uuid.png
        String realPath = path +File.separator+ callBackPath;

        File destFile = new File(realPath);
        //判断文件父目录是否存在
        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(destFile);
            return callBackPath;
        }catch(Exception e){
            logger.error("file upload failure:{}",e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("aaa"+File.separator+"bbb");
    }



}
