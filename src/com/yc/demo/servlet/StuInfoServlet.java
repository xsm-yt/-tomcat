package com.yc.demo.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.SmartUploadException;
import com.yc.demo.dao.StuInfoDao;
import com.yc.demo.entity.StuInfo;
import com.yc.demo.util.FileUploadUtil;
import com.yc.demo.util.StringUtil;

@WebServlet("/stu")
public class StuInfoServlet extends BasicServlet{
	

	private static final long serialVersionUID = -5232197898101507228L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		
		if("add".equals(op)) {//添加学生信息
			add(request, response);
		}else if("finds".equals(op)) {
			finds(request,response);
		} else if("findByPage".equals(op)) { //分页查询	
			findByPage(request,response);
		}
	}

	private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		
		StuInfoDao stuInfoDao=new StuInfoDao();
		
		List<StuInfo> list=stuInfoDao.find(page, rows);
		
		this.send(response,list);
		
	}

	private void finds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));		
		
		StuInfoDao stuInfoDao=new StuInfoDao();
		int total=stuInfoDao.getTotal();
		
		List<Map<String,String>> list=stuInfoDao.findByPage(page, rows);
		this.send(response, total,list);
	
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileUploadUtil fuu=new FileUploadUtil();
		PageContext pagecontext=JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 2048, true);
		try {
			Map<String,String> map=fuu.upload(pagecontext);
			StuInfoDao stuinfoDao=new StuInfoDao();
			int result=stuinfoDao.add(map.get("cid"),map.get("sname"),map.get("age"),map.get("tel"),map.get("addr"),map.get("birth"),map.get("photo"));
			this.send(response, result);
		} catch (ServletException | IOException | SmartUploadException e) {
			e.printStackTrace();
		}
		
		/*//获取
		String cid=request.getParameter("cid");
		String sname=request.getParameter("sname");
		String age=request.getParameter("age");
		String tel=request.getParameter("tel");
		String addr=request.getParameter("addr");
		String birth=request.getParameter("birth");
		String photo=request.getParameter("photo");
		if(StringUtil.checkNull(cid,sname)) {
			this.send(response, -1);
			return;
		}
		
		//运
		StuInfoDao stuinfoDao=new StuInfoDao();
		int result=stuinfoDao.add(cid, sname, age, tel, addr, birth, photo);
		
		//转
		this.send(response, result);*/
	}

}
