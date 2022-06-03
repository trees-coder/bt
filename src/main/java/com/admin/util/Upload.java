package com.admin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**

 * @Description: TODO(图片上传)
 */
public class Upload {
    //tomact路径
    public static String base_dir = "D:/javaWork/apache-tomcat-8.5.32/webapps/ROOT/img";
    //本地文件路径
    public static String local_base_dir ="F:/travel-website/src/main/webapp/img/";
    public static final String RIMAGE     = "/product/small";
    public static final String BIGPATH    = "/product/size4";
    public static final String SMALLPATH  = "/product/size2";
    public static final String HIMAGE     = "/hotel";
//F:/travel-website/src/main/webapp/img/
    public static String uploadImg(String filepath,MultipartFile multipartFile)throws Exception{
        String filename = "";
        //生成uuid作为文件名
        String uuid = UUID.randomUUID().toString().replace("-","");
        //获取文件类型
        String contentType = multipartFile.getContentType();
        //获取文件后缀名
        String suffixName = contentType.substring(contentType.indexOf("/")+1);
        //生成新的文件名
        filename = uuid+"."+suffixName;
        //保存文件到路径
        multipartFile.transferTo(new File(base_dir+filepath,filename));
        multipartFile.transferTo(new File(local_base_dir+filepath,filename));


        System.out.println("-------------------------"+filepath);
        return filename;
    }
}
