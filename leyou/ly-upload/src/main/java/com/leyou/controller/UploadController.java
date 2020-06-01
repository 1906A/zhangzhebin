package com.leyou.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {
    public static  final List<String> FILE_TYPE= Arrays.asList("jpg","png");
    @Value("${user.httpImageYuming}")
    private String httpImage;
    @RequestMapping("image")
    public String uploadImage(@RequestParam("file") MultipartFile file){

        try {
            String filename=file.getOriginalFilename();
            String type=filename.substring(filename.lastIndexOf(".")+1);
            String filetype=StringUtils.substringAfterLast(filename,".");
            System.out.println("type="+type);
            System.out.println("filetype="+filetype);
            if(!FILE_TYPE.contains(filetype)){
                return null;
            }
            BufferedImage bufferedImage=ImageIO.read(file.getInputStream());
            if(bufferedImage==null){
                return null;
            }
            String filePath=System.currentTimeMillis()+filename;
            file.transferTo(new File("D:/upload/"+filePath));
            //加载客户端配置文件，配置文件中指明了tracker服务器的地址
            //ClientGlobal.init("fastdfs.conf");
            //验证配置文件是否加载成功
            //System.out.println(ClientGlobal.configInfo());

            //创建TrackerClient对象，客户端对象
            //TrackerClient trackerClient = new TrackerClient();

            //获取到调度对象，也就是与Tracker服务器取得联系
            //TrackerServer trackerServer = trackerClient.getConnection();

            //创建存储客户端对象
            //StorageClient storageClient = new StorageClient(trackerServer,null);

            //String[] upload_file = storageClient.upload_file("D:/test.jpg", "jpg", params);
            //String[] upload_file=storageClient.upload_appender_file(file.getBytes(),filetype,null);



            //for (String string : upload_file) {
               // System.out.println(string);
            //}
            return httpImage+filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
