package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */

	private String baseSql =
			"SELECT ent_year,test.student_no ,student.name,test.class_num,test.no,test.point FROM TEST inner join student on test.student_no = student.no";


	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();

		try {
			// リザルトセットを全件走査
			while(rSet.next()) {
				// 学生インスタンスを初期化
				TestListSubject testlistsubject = new TestListSubject();
				// 学生インスタンスに検索結果をセット
				testlistsubject.setEntYear(rSet.getInt("ENT_YEAR"));
				testlistsubject.setStudentNo(rSet.getString("STUDENT_NO"));
				testlistsubject.setStudentName(rSet.getString("NAME"));
				testlistsubject.setClassNum(rSet.getString("CLASS_NUM"));
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


	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;

		// SQL文の条件
		String condition = " where ent_year = ? and test.class_num = ? and subject_cd = ? and test.school_cd = ?";
		// SQL文のソート
		String order = " order by no asc";


		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql+condition+order);
			System.out.print("hello1");

			// プリペアードステートメントに入学年度をバインド
			statement.setInt(1, entYear);
			System.out.print("hello1");

			statement.setString(2, classNum);
			System.out.print("hello1");

			statement.setString(3, subject.getCd());//error
			System.out.print("hello1");

			statement.setString(4, school.getCd());


			System.out.print("hello1");

			// プライベートステートメントを実行
			rSet = statement.executeQuery();

			// リストへの格納処理を実行
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}

}
