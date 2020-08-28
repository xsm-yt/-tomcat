package com.yc.demo.dao;

import java.util.List;
import java.util.Map;

import com.yc.demo.entity.StuInfo;

public class StuInfoDao{

	public int add(String cid,String sname,String age,String tel,String addr,String birth,String photo){
		DBHelper db=new DBHelper();
		String sql="insert into stuinfo values(0,?,?,?,?,?,?,?)";
		return db.update(sql, cid,sname,age,tel,addr,birth,photo);
	}



	public List<Map<String,String>> findByPage(int page,int rows){
		DBHelper db=new DBHelper();
		String sql="select sid,s.cid,cname,sname,age,tel,addr,birth,photo from stuinfo s,classinfo c where s.cid=c.cid limit ?,?";
		return db.gets(sql, (page-1)*rows,rows);
		
	}
	
	public int getTotal() {
		DBHelper db=new DBHelper();
		String sql="select count(sid) from stuinfo";
		return db.total(sql);
	}



	public List<StuInfo> find(int page, int rows) {
		DBHelper db=new DBHelper();
		String sql="select sid,s.cid,cname,sname,age,tel,addr,birth,photo from stuinfo s,classinfo c where s.cid=c.cid limit ?,?";
		return db.finds(StuInfo.class, sql,(page-1)*rows,rows);
	}
}