package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao{


	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from test ";
	/**
	 * getメソッド
	 *
	 * @param student:Student
	 *            学生
	 *         subject:Subject
	 *            科目
	 *         school:School
	 *         	  学校
	 *         no:int
	 *            回数
	 *
	 * @return テストのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {

		// testをインスタンス化
		Test test = new Test();
		// データベースへのコネクションを確立
		Connection connection = getConnection();

		// sql条件
		String condition = "where student_no=? and subject_cd=? and school_cd=? and no=? ";

		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition);
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, student.getNo());//学生
			statement.setString(2, subject.getCd());//科目
			statement.setString(3, school.getCd());//学校
			statement.setInt(4, no);//回数
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if ( rSet.next()) {
				// リザルトセットが存在する場合
				// テストインスタンスに検索結果をセット
				test.setStudent(student);
				test.setSchool(school);
				test.setSubject(subject);
				test.setNo(no);
				test.setPoint(rSet.getInt("point"));
			} else {
				// リザルトセットが存在しない場合
				// テストインスタンスにnullをセット
				test = null;
			}
		} catch (Exception e){
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
		return test;
	}

	/**
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @param school:School
	 *            学校
	 * @return テストのリスト:List<Test> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {

		// テストリストを初期化
		List<Test> list = new ArrayList<>();
		try {
			// リザルトセットを全件走査
			while(rSet.next()) {

				Test test = new Test();// テストインスタンスを初期化
				StudentDao studentDao = new StudentDao();// 学生Daoを初期化
				SubjectDao subjectDao = new SubjectDao();// 科目Daoを初期化
				// テストインスタンスに検索結果をセット
				test.setStudent(studentDao.get(rSet.getString("student_no")));//学生
				test.setPoint(rSet.getInt("point"));//得点
				test.setNo(rSet.getInt("no"));//回数
				test.setClassNum(rSet.getString("class_num"));//クラス
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));//科目
				test.setSchool(school);//学校
				list.add(test);//テストリストに追加
			}
		} catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * filterメソッド
	 *
	 * @param entYear:int
	 *            入学年度
	 * @param classNum:String
	 *            クラス番号
	 * @param subject:Subject
	 *            科目
	 * @param num:int
	 *            回数
	 * @param school:School
	 *            学校
	 *
	 * @return テストのリスト:List<Test> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "where ent_year=? and student.class_num=? and subject_cd=? and test.no=? and student.school_cd=? ";
		// SQL文のソート(学生番号順)
		String order = "order by student_no asc";

		try {
			// プリペアードステートメントにSQL文をセット（得点がnullの時-1に置換する）
			statement = connection.prepareStatement("select ent_year,student.class_num, student.no as student_no, subject_cd , test.no, COALESCE(point, -1)as point from test right outer join student on test.student_no = student.no " + condition + order);

			// プリペアードステートメントに学校コードをバインド
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			statement.setString(5, school.getCd());

			// プライベートステートメントを実行
			rSet = statement.executeQuery();

			// リストへの格納処理を実行
			list = postFilter(rSet, school);
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
//
	public boolean save(List<Test> list) throws Exception{
		// コネクションを確立

		// プリペアードステートメント
//		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		for (Test test:list) {
			Connection connection = getConnection();
			try{
				boolean bool = save(test, connection);
				if(bool != true){
					count++;
				}
			} catch (Exception e) {
				throw e;
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
		if (count == 0) {
			// 実行件数が一件以上ある場合
			return true;
		} else {
			// 実行件数が０件の場合
			return false;
		}
	}


	private boolean save(Test test, Connection connection) throws Exception{
//		// コネクションを確立
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		try {
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
			// データベースからテストを取得
			if (old == null) {
				// テストが存在しない場合
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
				// 	プリペアードステートメントに値をバインド
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			} else {
				// テストが存在した場合
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=? ");		 	//プリペアードステートメントに値をバインド
				if(test.getPoint() > 100 || test.getPoint() < 0){
					statement.setString(1, null);
				}else{
					statement.setInt(1, test.getPoint());
				}
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());
			}
			// プリペアードステートメントを実行
			count = statement.executeUpdate();
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
		if (count > 0) {
			// 実行件数が一件以上ある場合
			return true;
		} else {
			// 実行件数が０件の場合
			return false;
		}
	}


//	public boolean delete(List<Test> list) {
//
//	}
//
//	private boolean delete(Test test, Connection connection) {
//
//	}
//
//
//
//
//}
}