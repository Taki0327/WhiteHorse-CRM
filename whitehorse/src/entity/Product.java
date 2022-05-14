package entity;

public class Product {
	private int proid;
	private String proname;
	private String promoney;
	private String departid;
	public Product() {
	}
	public int getProid() {
		return proid;
	}
	public void setProid(int proid) {
		this.proid = proid;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getPromoney() {
		return promoney;
	}
	public void setPromoney(String promoney) {
		this.promoney = promoney;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public Product(int proid, String proname, String promoney, String departid) {
		this.proid = proid;
		this.proname = proname;
		this.promoney = promoney;
		this.departid = departid;
	}
}
