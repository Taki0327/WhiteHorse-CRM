package entity;

public class Custom {
	private int cusid;
	private String cusname;
	private String custime;
	private String cusconsum;
	public Custom() {
	}
	public Custom(int cusid, String cusname, String custime, String cusconsum) {
		super();
		this.cusid = cusid;
		this.cusname = cusname;
		this.custime = custime;
		this.cusconsum = cusconsum;
	}
	public int getCusid() {
		return cusid;
	}
	public void setCusid(int cusid) {
		this.cusid = cusid;
	}
	public String getCusname() {
		return cusname;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	public String getCustime() {
		return custime;
	}
	public void setCustime(String custime) {
		this.custime = custime;
	}
	public String getCusconsum() {
		return cusconsum;
	}
	public void setCusconsum(String cusconsum) {
		this.cusconsum = cusconsum;
	}

}
