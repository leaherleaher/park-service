package com.ttg.service.park.intelligent.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title: QrCode</p>
 * <p>Description: </p>
 *
 * @Author yangtao
 * @Date 2018/12/10 17:49
 */
@Api("工具类接口")
@RestController
@RequestMapping(value="/sys/util")
public class QrCode {

    @ApiOperation(value="根据url生成二维码",notes = "根据url生成二维码",httpMethod = "GET")
    @GetMapping(value="/showqrcode")
    public void getQrCode(HttpServletResponse response,String url) throws IOException {

        //从权限中获取id
        //UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();

        response.setContentType("image/png");
        response.addHeader("cache-control", "no-cache");
        response.addHeader("cache-control", "Private");

        //String url = payProperties.getFileServer()+"/app/download";//智慧停车APP下载链接  直接下载
        //url = "http://www.baidu.com";

        if (url != null && !"".equals(url)) {
            try(ServletOutputStream stream = response.getOutputStream())
            {
                response.setContentType("image/png");
                response.addHeader("cache-control", "no-cache");
                response.addHeader("cache-control", "Private");

                int width = 255;//图片的宽度
                int height = 255;//高度
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix m = writer.encode(url, BarcodeFormat.QR_CODE, height, width);
                MatrixToImageWriter.writeToStream(m, "png", stream);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}
