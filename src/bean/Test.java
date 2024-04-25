package bean;

import java.io.Serializable;

import javax.security.auth.Subject;

public class Test implements Serializable {

	/**
	 * 学生：Student
	 */
	private Student student;

	/**
	 * クラス番号:String
	 */
	private String class_num;

	/**
	 * 科目:Subject
	 */
	private Subject subject;

	/**
	 * 学校:School
	 */
	private School school;

	/**
	 * 回数:int
	 */
	private int no;

	/**
	 * 得点:int
	 */
	private int point;


	/**
	 * ゲッター・セッター
	 */
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getClass_num() {
		return class_num;
	}

	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

}
