package entity;

public class Depart {
    private int departid;
    private String departname;

    public int getDepartid() {
        return departid;
    }

    public void setDepartid(int departid) {
        this.departid = departid;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public Depart() {
    }
    public String toString(){
        return departname;
    }
    public Depart(int departid, String departname) {
        this.departid = departid;
        this.departname = departname;
    }
}
