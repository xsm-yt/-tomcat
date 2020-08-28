package com.yc.demo.entity;

import java.io.Serializable;

public class StuInfo implements Serializable {

	private static final long serialVersionUID = -7114670024779363L;
		private Integer sid;
		private Integer cid;
		private String sname;
		private Integer age;
		private String tel;
		private String addr;
		private String birth;
		private String photo;
		
		private String cname;

		@Override
		public String toString() {
			return "StuInfo [sid=" + sid + ", cid=" + cid + ", sname=" + sname + ", age=" + age + ", tel=" + tel
					+ ", addr=" + addr + ", birth=" + birth + ", photo=" + photo + ", cname=" + cname + "]";
		}

		public Integer getSid() {
			return sid;
		}

		public void setSid(Integer sid) {
			this.sid = sid;
		}

		public Integer getCid() {
			return cid;
		}

		public void setCid(Integer cid) {
			this.cid = cid;
		}

		public String getSname() {
			return sname;
		}

		public void setSname(String sname) {
			this.sname = sname;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getBirth() {
			return birth;
		}

		public void setBirth(String birth) {
			this.birth = birth;
		}

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getCname() {
			return cname;
		}

		public void setCname(String cname) {
			this.cname = cname;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public StuInfo(Integer sid, Integer cid, String sname, Integer age, String tel, String addr, String birth,
				String photo, String cname) {
			super();
			this.sid = sid;
			this.cid = cid;
			this.sname = sname;
			this.age = age;
			this.tel = tel;
			this.addr = addr;
			this.birth = birth;
			this.photo = photo;
			this.cname = cname;
		}

		public StuInfo() {
			super();
		}

		
}
