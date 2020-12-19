package com.gabestacking.recydisc.download;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@WebServlet("/servlet/listFileServlet")
public class ListFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取文件上传的目录
        String uploadFilePath=this.getServletContext().getRealPath("/WEB-INF/upload");

        //存储要下载的文件名
        Map<String,String> fileMap=new HashMap<>();

        //递归遍历filepath目录下的所有文件和目录，将文件夹和文件名存储到map集合中
        fileList(new File(uploadFilePath),fileMap);

        //将map集合发送到listfile.jsp页面进行显示
        req.setAttribute("fileMap",fileMap);

        req.getRequestDispatcher("/fileList.jsp").forward(req,resp);


    }

    public void fileList(File file,Map fileMap){
        //如果file是一个目录
        if(!file.isFile()){
            //列出该目录下的所有文件和目录
            File[] files=file.listFiles();
            //遍历files[]数组
            for (File f:files) {
                //递归
                fileList(f,fileMap);
            }
        }else{
            String realName=file.getName().substring(file.getName().lastIndexOf("_")+1);
            fileMap.put(file.getName(),realName);
        }
    }
}
