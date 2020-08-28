package com.yc.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.demo.dao.ClassInfoDao;

@WebServlet("/cls")
public class ClassInfoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7106476294376950244L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		super.service(req, resp);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    doPost (req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		
		if("findAll".equals(op)) {
			findAll(request,response);
		}
	}
	private void findAll(HttpServletRequest request,HttpServletResponse response)throws IOException {
		ClassInfoDao classInfoDao=new ClassInfoDao();
		List<Map<String,String>> list=classInfoDao.finds();
		StringBuffer sbf=new StringBuffer();
		
		/*list.forEach(item ->{
			sbf.append("<option value='").append(item.get("cid")).append("'>").append(item.get("cname")).append("</option");
		});*/
		sbf.append("[");
		list.forEach(item -> sbf.append("{\"cid\":\"").append(item.get("cid")).append("\",\"cname\":\"").append(item.get("cname")).append("\"},"));
		String str =sbf.substring(0,sbf.lastIndexOf(","))+"]";
		
		//将数据返回给请求者
		PrintWriter out=response.getWriter();
		out.println(str);
		out.flush();
	}

}