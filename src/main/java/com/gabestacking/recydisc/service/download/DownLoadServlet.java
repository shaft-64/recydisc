package com.gabestacking.recydisc.service.download;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet("/servlet/downLoadServlet")
public class DownLoadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //得到要下载的文件名
        String username="rainyun";
        String rootDir="D:\\NetDisk";
        String fileName=req.getParameter("fileName");
        fileName=new String(fileName.getBytes("iso8859-1"),"UTF-8");
        String fileSaveRootPath=rootDir+"\\Resources";
        String path=fileSaveRootPath;
        //FIXME
        String fullPath=path+"\\"+username+"\\"+fileName;

        File file=new File(fullPath);

        if(!file.exists()){
            req.setAttribute("message","您要下载的资源已被删除!!!");
            req.getRequestDispatcher("/message.jsp").forward(req,resp);
            return;
        }

        resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        FileInputStream in=new FileInputStream(path+File.separator+username+File.separator+fileName);



        OutputStream os=resp.getOutputStream();

        byte[] bytes=new byte[1024];

        int len=0;

        while((len=in.read(bytes))>0){
            os.write(bytes);
        }

        in.close();

        os.close();
    }
}
