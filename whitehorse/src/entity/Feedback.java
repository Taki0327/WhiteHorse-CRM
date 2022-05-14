package entity;

public class Feedback {
	private int feedid;
	private String feedtxt;
	private String feedtime;
	private String feedworkname;
	public Feedback() {
	}
	public int getFeedid() {
		return feedid;
	}
	public void setFeedid(int feedid) {
		this.feedid = feedid;
	}
	public String getFeedtxt() {
		return feedtxt;
	}
	public void setFeedtxt(String feedtxt) {
		this.feedtxt = feedtxt;
	}
	public String getFeedtime() {
		return feedtime;
	}
	public void setFeedtime(String feedtime) {
		this.feedtime = feedtime;
	}

	public String getFeedworkname() {
		return feedworkname;
	}

	public void setFeedworkname(String feedworkname) {
		this.feedworkname = feedworkname;
	}

	public Feedback(String feedtxt, String feedtime, String feedworkname) {
		this.feedtxt = feedtxt;
		this.feedtime = feedtime;
		this.feedworkname = feedworkname;
	}
}
