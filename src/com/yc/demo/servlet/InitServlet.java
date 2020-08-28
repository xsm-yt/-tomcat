package com.yc.demo.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.yc.demo.util.FileUploadUtil;
import com.yc.demo.util.StringUtil;



//loadOnStartup 在项目启动时加载运行，数字越小越优先加载
//@WebServlet(name="initServlet",value="",loadOnStartup=1,initParams= {@WebInitParam(name="uploadPath",value="../files")})

public class InitServlet extends HttpServlet{

	private static final long serialVersionUID = -2061359045954226606L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String path=config.getInitParameter("uploadPath");
		if(StringUtil.checkNull(path)) { //如果没有给路径，就给一个默认路径
			path="../photos";
		}
		
		String temp=config.getServletContext().getRealPath("/");
		//判断这个路径在服务器中是否存在
		File file=new File(temp,path);
		if(!file.exists()) {
			file.mkdirs();
		}
		FileUploadUtil.PATH=path;
	}
}
