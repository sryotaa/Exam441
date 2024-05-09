package bean;

import java.util.Map;

public class TestListSubject {

	/**
	 * 入学年度:int
	 */
	private int entYear;

	/**
	 * 学生番号:String
	 */
	private String studentNo;

	/**
	 * 学生氏名:String
	 */
	private String studentName;

	/**
	 * クラス番号:String
	 */
	private String classNum;

	/**
	 * 点数:Map
	 */
	private Map<Integer, Integer> points;


	public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Map<Integer, Integer> getPoints() {
		return points;
	}

	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	public String getPoint(int key) {
//		得点マップから値を取得
		Integer k = points.get(key);
		if(k == null) {
			// 得点マップに値が存在しなかった場合、"-"を返却
			return "-";
		} else {
			// 得点マップに値が存在した場合、文字列で得点を返却
			return k.toString();
		}
	}

	public void putPoint(int key, int value) {
		points.get(key);
	}

}
