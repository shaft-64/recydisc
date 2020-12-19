package com.gabestacking.recydisc.download;

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
        String fileName=req.getParameter("fileName");
        System.out.println("hashName:"+fileName);
        fileName=new String(fileName.getBytes("iso8859-1"),"UTF-8");
        //得到上传文件的根目录
        String fileSaveRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");

        //处理文件名
        String realName=fileName.substring(fileName.indexOf("_")+1);



        //通过文件名找到文件所在目录
        String path=findFileSavePathByFileName(fileName,fileSaveRootPath);

        //FIXME
        System.out.println(path);
        System.out.println(path+File.separator+fileName);
        File file=new File(path+File.separator+fileName);


        if(!file.exists()){
            req.setAttribute("message","您要下载的资源已被删除!!!");
            req.getRequestDispatcher("/message.jsp").forward(req,resp);
            return;
        }

        System.out.println(URLEncoder.encode(realName, "UTF-8"));

        resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));

        FileInputStream in=new FileInputStream(path+File.separator+fileName);

        OutputStream os=resp.getOutputStream();

        byte[] bytes=new byte[1024];

        int len=0;

        while((len=in.read(bytes))>0){
            os.write(bytes);
        }

        in.close();

        os.close();



    }

    public String findFileSavePathByFileName(String fileName,String fileSaveRootPath){

        int hashcode = fileName.hashCode();

        int dir1 = hashcode&0xf;

        int dir2 = (hashcode&0xf0)>>4;

        String dir = fileSaveRootPath + "\\" + dir1 + "\\" + dir2;

        File file = new File(dir);

        if(!file.exists()){

            file.mkdirs();

        }

        return dir;

    }
}
