package com.yc.demo.dao;

import java.util.List;
import java.util.Map;

public class ClassInfoDao {
	public List<Map<String,String>> finds(){
		DBHelper db=new DBHelper();
		String sql="select cid,cname from classinfo";
		return db.gets(sql);
	}
}
