package entity;

public class Worker {
	private int workid;
	private String workname;
	private String workdepart;
	private double workprice;
	public int userid;
	public Worker(int workid,int userid,String workname,  String workdepart, double workprice) {
		super();
		this.userid=userid;
		this.workid = workid;
		this.workname = workname;
		this.workdepart = workdepart;
		this.workprice = workprice;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid() {
		this.userid=userid;
	}
	public int getWorkid() {
		return workid;
	}
	public void setWorkid(int workid) {
		this.workid = workid;
	}
	public String getWorkname() {
		return workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public String getWorkdepart() {
		return workdepart;
	}
	public void setWorkdepart(String workdepart) {
		this.workdepart = workdepart;
	}
	public double getWorkprice() {
		return workprice;
	}
	public void setWorkprice(double workprice) {
		this.workprice = workprice;
	}

	public Worker() {
	}
}
