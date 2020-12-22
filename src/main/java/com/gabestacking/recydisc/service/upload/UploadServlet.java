package com.gabestacking.recydisc.service.upload;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/servlet/uploadServlet")
public class UploadServlet extends HttpServlet {

    private final int BUF_SIZE=1024;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //初始化
        String username = request.getParameter("username");
        String rootDir="D:\\NetDisk";
        String savePath=rootDir+"\\Resources";
        String userSavePath = savePath+"\\"+username;
        String tempPath=rootDir+"\\temp";

        initStorage(new File(userSavePath));
        File file = new File(savePath);
        initStorage(file);
        file=new File(tempPath);
        initStorage(file);

        String message = "";



        try {
            //fileupload类的操作
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(1024*100);
            diskFileItemFactory.setRepository(file);
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            fileUpload.setHeaderEncoding("UTF-8");


            //监听文件上传进度
            fileUpload.setProgressListener((pBytesRead, pContentLength, arg2) -> System.out.println( pBytesRead+"/"+(double)pContentLength/1024/1024+"MB" ));


            //判断请求类型
            if(!fileUpload.isMultipartContent(request)){
                System.out.println(fileUpload.isMultipartContent(request));
                return;
            }


            //文件大小限制
            fileUpload.setFileSizeMax(1024*1024*100);//单个文件
            fileUpload.setSizeMax(1024*1024*1024);//所有文件



            //解析上传的数据
            List<FileItem> list = fileUpload.parseRequest(request);

            for (FileItem item : list) {

                System.out.println(item);
                if(item.isFormField()){

                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");

                    System.out.println(name+":"+value+":"+value1);
                }else{
                    String fileName = item.getName();
                    if(fileName==null||fileName.trim().equals("")){
                        continue;
                    }


                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                    //得到上传文件的扩展名，用于限制上传的文件类型
                    String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);



                    //获取item中的上传文件的输入流

                    InputStream is = item.getInputStream();


                    FileOutputStream fos = new FileOutputStream(userSavePath+File.separator+fileName);

                    System.out.println(userSavePath);

                    byte buffer[]=new byte[BUF_SIZE];

                    int len=0;

                    while ((len=is.read(buffer))>0){
                        fos.write(buffer,0,len);
                    }


                    //关闭通道，删除缓冲区文件(.tmp)
                    is.close();
                    fos.close();
                    item.delete();
                    message = "文件上传成功";
                }
            }

        }catch(ClassCastException e){

        }
        catch (FileUploadBase.FileSizeLimitExceededException e) {

            //单个文件超出最大值
            e.printStackTrace();
            message = "单个文件超出最大值！！！";
        }catch (FileUploadBase.SizeLimitExceededException e) {

            //上传文件的总的大小超出限制的最大值
            e.printStackTrace();
            message = "上传文件的总的大小超出限制的最大值！！！";
        }catch (FileUploadException e) {

            //未知错误
            e.printStackTrace();
            message = "文件上传失败";
        }finally {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(message);
        }
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void initStorage(File file){
        if(!file.exists()&&!file.isDirectory()){
            file.mkdirs();
        }
    }

}
