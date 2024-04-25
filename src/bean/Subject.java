package bean;

import java.io.Serializable;

public class Subject implements Serializable{


	//科目コード
	private String no;

	//科目名
	private String name;




	//ゲッタ、セッタ

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}





}
