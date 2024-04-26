package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListSubject;

public class TestListSubjectDao {

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */

	private String baseSql = "";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();

		try {
			// リザルトセットを全件走査
			while(rSet.next()) {
				// 学生インスタンスを初期化
				TestListSubject testlistsubject = new TestListSubject();
				// 学生インスタンスに検索結果をセット
				testlistsubject.setEntYear(rSet.getInt("entYear"));
				testlistsubject.setStudentNo(rSet.getString("studentNo"));
				testlistsubject.setStudentName(rSet.getString("studentName"));
				testlistsubject.setClassNum(rSet.getString("classNum"));
//				testlistsubject.setPoints(Map<>);
				//リストに追加
				list.add(testlistsubject);
			}
		} catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * filterメソッド 入学年度、クラス番号、科目を指定して成績一覧を取得する
	 *
	 * @param entYear:int
	 *            入学年度
	 * @param classNum:String
	 *            クラス番号
	 * @param isAttend:boolean
	 *            科目
	 * @throws Exception
	 */


	public List<Student> filter(int entYear, String classNum, Map<> points) throws Exception {


	}

}
