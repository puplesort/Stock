package Board;

import java.sql.Date;

public class Board {
	private int num;
	private String writer;
	private Date wdate;
	private String title;
	private String content;
	private String company_name;

	public Board(int num, String writer, Date wdate, String title, String content, String company_name) {
		super();
		this.num = num;
		this.writer = writer;
		this.wdate = wdate;
		this.title = title;
		this.content = content;
		this.company_name = company_name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getWdate() {
		return wdate;
	}

	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Override
	public String toString() {
		return "Board [num=" + num + ", writer=" + writer + ", wdate=" + wdate + ", title=" + title + ", content="
				+ content + ", company_name=" + company_name + "]";
	}

}
