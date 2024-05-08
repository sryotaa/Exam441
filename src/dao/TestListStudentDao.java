package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */

	private String baseSql = "select subject.name, test.subject_cd, test.no, test.point from test left join subject on test.school_cd = subject.school_cd and test.student_no=?";


	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();

		try {
			// リザルトセットを全件走査
			while(rSet.next()) {
				// 学生インスタンスを初期化
				TestListStudent testliststudent = new TestListStudent();
				// 学生インスタンスに検索結果をセット
				System.out.print("yuuyhhyuhuy");
				testliststudent.setSubjectName(rSet.getString("NAME"));
				testliststudent.setSubjectCd(rSet.getString("SUBJECT_CD"));
				testliststudent.setNum(rSet.getInt("NO"));
				testliststudent.setPoint(rSet.getInt("POINT"));
				//リストに追加
				list.add(testliststudent);
			}
		} catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}




	/**
	 * filterメソッド 学生番号を指定し、学生別成績一覧を取得する
	 *
	 * @param student:Student
	 *            学生番号
	 * @throws Exception
	 */

	public List<TestListStudent> filter(Student student)throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;


		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql);

			System.out.print(student.getName());
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, student.getNo());
			System.out.print(student.getName());
			// プライベートステートメントを実行
			rSet = statement.executeQuery();
			System.out.print(student.getName());
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

