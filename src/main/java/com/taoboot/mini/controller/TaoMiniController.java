package com.taoboot.mini.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taoboot.mini.core.constant.CacheConstant;
import com.taoboot.mini.core.ehcache.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

/**
 * 〈小程序入口〉
 *
 * @author chentao
 * @create 2019/5/16
 * @since 1.0.0
 */
@Controller
@EnableAsync
@RequestMapping(value="/mini")
public class TaoMiniController {

    private Logger logger = LoggerFactory.getLogger(TaoMiniController.class);

    @RequestMapping(value="/uploadFile", method= RequestMethod.POST)
    public void uploadFile(HttpServletRequest request, HttpServletResponse response){
        logger.info("开始上传");
        try {
            //获取文件需要上传到的路径
            File directory = new File("..");
            String path = directory.getCanonicalPath() + "/upload";
            logger.info("上传地址:{}", path);

            // 判断存放上传文件的目录是否存在（不存在则创建）
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }

            request.setCharacterEncoding("utf-8"); //设置编码
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            JSONArray array = new JSONArray();
            while (iterator.hasNext()) {
                HashMap<String, Object> res = new HashMap<String, Object>();
                MultipartFile file = req.getFile(iterator.next());
                // 获取文件名
                String fileNames = file.getOriginalFilename();
                logger.info("fileNames文件名:{}", fileNames);
                int split = fileNames.lastIndexOf(".");
                //获取上传文件的后缀
                String extName = fileNames.substring(split + 1, fileNames.length());
                //申明UUID
                String uuid = UUID.randomUUID().toString().replace("-", "");

                //组成新的图片名称
                String newName = uuid + "." + extName;
                logger.info("新名字:{}", newName);

                String destPath = path + newName;
                logger.info("最终信息:{}", destPath);

                //真正写到磁盘上
                File file1 = new File(destPath);
                OutputStream out = new FileOutputStream(file1);
                out.write(file.getBytes());
                res.put("url", destPath);

                array.add(res);

                out.close();
            }

            PrintWriter printWriter = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            //printWriter.write();
            printWriter.flush();



        } catch (IOException e) {
            e.printStackTrace();
            logger.error("出错了", e);
        }
    }

    @ResponseBody
    @RequestMapping(value="/uploadFile", method = RequestMethod.GET)
    public String uploadFileGet(){
        return "can't support GET";
    }


    @ResponseBody
    @RequestMapping(value="/uploadFileSpring", method = RequestMethod.POST)
    public void uploadFileSpring(HttpServletRequest request, HttpServletResponse response){
        JSONObject ret = new JSONObject();
        try{
            String key = request.getSession().getId();
            String detailPath = (String) CacheUtil.getInstance().get(CacheConstant.WXOPENID.getCode(), key);
            //判断是否有缓存信息
            logger.info("缓存ID:{}, openId:{}", key, detailPath);
            if(StringUtils.isEmpty(detailPath)){
                logger.error("未登入");
                throw new Exception("未登入");
            }

            //获取当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String time = df.format(new Date());// new Date()为获取当前系统时间
            //获取文件需要上传到的路径
            File directory = new File("/taominiDown");
            String path = directory.getCanonicalPath() + "/upload/" + detailPath + "/";
            logger.info("上传地址:{}", path);

            // 判断存放上传文件的目录是否存在（不存在则创建）
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }

            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            logger.info("是否支持上传:{}", multipartResolver.isMultipart(request));
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();
                logger.info("是否有文件信息:{}",iter.hasNext());
                while(iter.hasNext()) {
                    //一次遍历所有文件
                    MultipartFile file = multiRequest.getFile(iter.next().toString());
                    if (file != null) {
                        logger.info("文件名字:{}", file.getOriginalFilename());
                        path += file.getOriginalFilename();
                        logger.info("上传地址:{}", path);
                        //上传
                        try {
                            file.transferTo(new File(path));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        logger.info("file is null.");
                    }

                }
            }
            ret.put("status", "succ");

            this.doshell("/home/shell/chownShell.sh");

        }catch (IOException e){
            logger.error("错误信息", e);
            ret.put("status", "fail");
        }catch (Exception e){
            logger.error("错误信息", e);
            ret.put("status", "fail");
        }

        //回传信息
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        printWriter.write(JSON.toJSONString(ret));
        printWriter.flush();
    }

    /**
     * 执行shell脚本
     * @param path
     */
    @Async
    public void doshell(String path){
        try {
            Process process = Runtime.getRuntime().exec(path);
            process.waitFor();
        } catch (IOException e) {
            logger.error("执行脚本出错!", e);
        } catch (InterruptedException e) {
            logger.error("process错误", e);
        }
    }
}