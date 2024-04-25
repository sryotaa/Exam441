package bean;

import java.io.Serializable;

public class TestListStudent implements Serializable {

	/**
	 * 入学年度:int
	 */
	private String subjectName;

	/**
	 * クラス番号:String
	 */
	private String subjectCd;

	/**
	 * 学生番号:String
	 */
	private int num;

	/**
	 * 氏名:String
	 */
	private int point;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}


}
