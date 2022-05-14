package entity;


public class Bill {
	private int billid;
	private String proname;
	private String departid;
	private String workname;
	private String spendtime;
	private String finishtime;
	private String billmoney;

	public int getBillid() {
		return billid;
	}

	public void setBillid(int billid) {
		this.billid = billid;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getWorkname() {
		return workname;
	}

	public void setWorkname(String workname) {
		this.workname = workname;
	}

	public String getSpendtime() {
		return spendtime;
	}

	public void setSpendtime(String spendtime) {
		this.spendtime = spendtime;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}

	public String getBillmoney() {
		return billmoney;
	}

	public void setBillmoney(String billmoney) {
		this.billmoney = billmoney;
	}

	public Bill() {
	}

	public Bill(int billid, String proname, String workname, String spendtime, String finishtime, String billmoney) {
		this.billid = billid;
		this.proname = proname;
		this.workname = workname;
		this.spendtime = spendtime;
		this.finishtime = finishtime;
		this.billmoney = billmoney;
	}

	public Bill(String proname, String spendtime, String billmoney,String departid) {
		this.proname = proname;
		this.spendtime = spendtime;
		this.billmoney = billmoney;
		this.departid=departid;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}
}
