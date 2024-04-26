package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;
import bean.Test;

public class TestDao extends Dao{


	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from test where student_no=? ";


	/**
	 * getメソッド
	 *
	 * @param no:String
	 *            学生番号
	 * @return 学生クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {

		// testをインスタンス化
		Test test = new Test();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from test where no=?");
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();
			// 学生Daoを初期化
//			StudentDao studentDao = new StudentDao();
			// 科目Daoを初期化
//			SubjectDao subjectDao = new SubjectDao();

			if ( rSet.next()) {
				// リザルトセットが存在する場合
				// テストインスタンスに検索結果をセット
//				test.setStudent(studentDao.get(rSet.getString("student")));
				test.setClassNum(rSet.getString("class_num"));
//				test.setSubject(SubjectDao.get(rSet.getString("subject")));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setPoint(rSet.getInt("point"));
				test.setNo(rSet.getInt("no"));

			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
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
	private List<Test> postFilter(ResultSet rSet, School school, Subject subject) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			// リザルトセットを全件走査
			while(rSet.next()) {
				// 学生インスタンスを初期化
				Test test = new Test();
				// 学生インスタンスに検索結果をセット
//				test.setStudent(rSet.getString("student"));
				test.setClassNum(rSet.getString("class_num"));
//				test.setSubject(rSet.getInt("subject"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
//				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				//リストに追加
				list.add(test);
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
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
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
		String condition = "and ent_year=? and class_num=? and subject_cd=?";
		// SQL文のソート
		String order = "order by no asc";

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(4, subject.getNo());
			// プライベートステートメントを実行
			rSet = statement.executeQuery();

			// リストへの格納処理を実行
			list = postFilter(rSet, school, subject);
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


	public boolean save(List<Test> list) {
		// コネクションを確立
				Connection connection = getConnection();
				// プリペアードステートメント
				PreparedStatement statement = null;
				// 実行件数
				int count = 0;
				try {
					// データベースから学生を取得
					while(list){
					Test old = list;
					if (old == null) {
						// 学生が存在しない場合
						// プリペアードステートメントにINSERT文をセット
						statement = connection.prepareStatement("insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
						// 	プリペアードステートメントに値をバインド
						statement.setString(1, old.getStudent().getNo());
						statement.setString(2, old.getSubject().getCd());
						statement.setString(3, old.getSchool().getCd());
						statement.setInt(4, old.getNo());
						statement.setInt(5, old.getPoint());
						statement.setString(6, old.getClassNum());

					} else {
						// 学生が存在した場合
						// プリペアードステートメントにUPDATE文をセット
						statement = connection.prepareStatement("update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
//					 	プリペアードステートメントに値をバインド
						statement.setString(1, student.getName());
						statement.setInt(2, student.getEntYear());
						statement.setString(3, student.getClassNum());
						statement.setBoolean(4, student.isAttend());
						statement.setString(5, student.getNo());
					}
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

	private boolean save(Test test, Connection connection) {

	}

	public boolean delete(List<Test> list) {

	}

	private boolean delete(Test test, Connection connection) {

	}




}
