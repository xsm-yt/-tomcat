package com.yc.demo.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class FileUploadUtil {
	public static String PATH="../pics"; //上传后的文件路径
	private static final String ALLOWEDLIST="gif,jpg,png,jpeg,doc,docx,xls,xlsx,txt";
	private static final int MAXFILESIZE=10*1024*1024;//单个文件的大小
	private static final  int TOTAMAXSIZE=100*1024*1024;//每次允许的上传的最大大小
	private String basePath;
	
	@SuppressWarnings("unchecked")
	public Map<String,String> upload(PageContext pagecontext) throws ServletException, IOException, SmartUploadException{
		Map<String,String> map =new HashMap<String,String>();
		
		//实例化上传组件
		SmartUpload su=new SmartUpload();
		su.initialize(pagecontext);
		
		//设置参数
		su.setMaxFileSize(MAXFILESIZE);
		su.setTotalMaxFileSize(TOTAMAXSIZE);
		su.setAllowedFilesList(ALLOWEDLIST);
		//su.setDeniedFilesList(s);不许上传是文件猴嘴
		su.setCharset("utf-8");
		su.upload(); //开始上传
		
		
		//获取非文件参数
		Request req=su.getRequest();
		Enumeration<String> enums=req.getParameterNames();
		
		String name=null;
		while(enums.hasMoreElements()) {
			name=enums.nextElement();
			map.put(name, req.getParameter(name));
		}
		
		//处理上传文件
		Files files=su.getFiles();
		if(files==null || files.getCount()<=0) {
			return map;
		}
		
		Collection<File> fls=files.getCollection();
		
		//获取保存文件的绝对路径
		basePath=pagecontext.getRequest().getRealPath("/");
		
		String fieldName=null;
		String fileName=null;
		String temp=null;
		String pathStr="";
		
		for(File f1:fls) {
			if(!f1.isMissing()) {
				temp=f1.getFieldName();
				if(StringUtil.checkNull(fieldName)) { //说明是第一个要上传的文件
					fieldName=temp;					
				}else {
					if(!temp.equals(fieldName)) {//说明此时是另一个文件文本框
						//首先需要将第一个文件文本框中的了内容存到map中
						map.put(fieldName, pathStr);
						pathStr=""; //初始化一下，准备存放下一个文件文本框中的文件上传后的路径
						fieldName=temp;
						
					}
				}
				//存到服务器中 -> 获取tomacat在服务器中的绝对路径		
				fileName=PATH+"/"+new Date().getTime()+"_"+f1.getFileName();		
				if(StringUtil.checkNull(pathStr)) {
					pathStr=fileName;
				}else {
					pathStr +=","+fileName;
				}
				
				//保存到服务区
				f1.saveAs(basePath+fileName,SmartUpload.SAVE_PHYSICAL);
			}
			
		}
		map.put(fieldName, pathStr);
		return map;
	}
	
	public <T> T upload(Class<T> clazz,PageContext pagecontext) throws Exception {
		T t=clazz.newInstance();
		//实例化上传组件
				SmartUpload su=new SmartUpload();
				su.initialize(pagecontext);
				
				//设置参数
				su.setMaxFileSize(MAXFILESIZE);
				su.setTotalMaxFileSize(TOTAMAXSIZE);
				su.setAllowedFilesList(ALLOWEDLIST);
				//su.setDeniedFilesList(s);不许上传是文件猴嘴
				su.setCharset("utf-8");
				su.upload(); //开始上传
				
				
				//获取非文件参数
				Request req=su.getRequest();
				Enumeration<String> enums=req.getParameterNames();
				
				//获取指定类中的所有方法
				Method[] methods=clazz.getMethods();
				Map<String,Method> setters=new HashMap<String,Method>();
				String methodName=null;
				
				for(Method md:methods) {
					methodName=md.getName();
					if(methodName.startsWith("set")) {
						setters.put(methodName, md);
					}
				}
				
				String name=null;
				Class<?>[] types=null;
				Class<?> type=null;
				Method method=null;
				
				while(enums.hasMoreElements()) {
					name=enums.nextElement();
					methodName="set" +name.substring(0,1).toUpperCase()+name.substring(1);
					
					method=setters.get(methodName);
					if(method==null) {
						continue;
					}
					
					types=method.getParameterTypes();
					if(types==null) {
						continue;
					}
					
					type=types[0];
					
					if(Integer.TYPE==type || Integer.class==type) {
						method.invoke(t, Integer.valueOf(req.getParameter(name)));
					} else if(Double.TYPE==type || Double.class==type) {
						method.invoke(t, Double.valueOf(req.getParameter(name)));
					} else if(Float.TYPE==type || Float.class==type) {
						method.invoke(t, Float.valueOf(req.getParameter(name)));
					} else {
						method.invoke(t, req.getParameter(name));
					}
				}
				
				//处理上传的文件
				Files files=su.getFiles();
				if(files==null || files.getCount()<=0) {
					return t;
				}
				Collection<File> fls=files.getCollection();
				
				basePath=pagecontext.getServletContext().getRealPath("/");
				
				String fieldName=null;
				String fileName=null;
				String temp=null;
				String pathStr="";
				
				for(File f1:fls) {
					if(!f1.isMissing()) {
						temp=f1.getFieldName();
						if(StringUtil.checkNull(fieldName)) { //说明是第一个要上传的文件
							fieldName=temp;					
						}else {
							if(!temp.equals(fieldName)) {//说明此时是另一个文件文本框
								//首先需要将第一个文件文本框中的了内容存到map中
								methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
							method=setters.get(methodName);
							if(method==null) {
								continue;
							}
							
							types=method.getParameterTypes();
							if(types==null) {
								continue;
							}
							method.invoke(t, pathStr);
							pathStr="";
							fieldName=temp;
							
						}
				}
						//存到服务器中 -> 获取tomacat在服务器中的绝对路径		
						fileName=PATH+"/"+new Date().getTime()+"_"+f1.getFileName();		
						if(StringUtil.checkNull(pathStr)) {
							pathStr=fileName;
						}else {
							pathStr +=","+fileName;
						}
						
						//保存到服务区
						f1.saveAs(basePath+fileName,SmartUpload.SAVE_PHYSICAL);
					}
				
				}
				methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
				method=setters.get(methodName);
				if(method==null) {
					return t;
				}
				
				types=method.getParameterTypes();
				if(types==null) {
					return t;
				}
				
				method.invoke(t, pathStr);
				return t;
	}
}
