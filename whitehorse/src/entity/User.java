package entity;

public class User {
	private String userid;
	private String uname;
	private String upwd;
	private String uphone;
	private String uide;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUide() {
		return uide;
	}
	public void setUide(String uide) {
		this.uide = uide;
	}

	public User() {
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	
}
