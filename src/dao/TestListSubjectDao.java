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
//
//public class TestListSubjectDao {
//
//	/**
//	 * baseSql:String 共通SQL文 プライベート
//	 */
//
//	private String baseSql = "select entyear, class_num, student_no, student_name, ";
//
//	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
//		// リストを初期化
//		List<TestListSubject> list = new ArrayList<>();
//
//		try {
//			// リザルトセットを全件走査
//			while(rSet.next()) {
//				// 学生インスタンスを初期化
//				TestListSubject testlistsubject = new TestListSubject();
//				// 学生インスタンスに検索結果をセット
//				testlistsubject.setEntYear(rSet.getInt("entYear"));
//				testlistsubject.setStudentNo(rSet.getString("studentNo"));
//				testlistsubject.setStudentName(rSet.getString("studentName"));
//				testlistsubject.setClassNum(rSet.getString("classNum"));
////				testlistsubject.setPoints(Map<>);
//				//リストに追加
//				list.add(testlistsubject);
//			}
//		} catch (SQLException | NullPointerException e){
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//
//	/**
//	 * filterメソッド 入学年度、クラス番号、科目を指定して成績一覧を取得する
//	 *
//	 * @param entYear:int
//	 *            入学年度
//	 * @param classNum:String
//	 *            クラス番号
//	 * @param isAttend:boolean
//	 *            科目
//	 * @throws Exception
//	 */
//
//
//	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
//
//		// リストを初期化
//		List<TestListSubject> list = new ArrayList<>();
//		// コネクションを確立
//		Connection connection = getConnection();
//		// プリペアードステートメント
//		PreparedStatement statement = null;
//		// リザルトセット
//		ResultSet rSet = null;
//
//		// SQL文の条件
//		String condition = "and ent_year=? and class_num=? ";
//		// SQL文のソート
//		String order = "order by no asc";
//
//
//		try {
//			// プリペアードステートメントにSQL文をセット
//			statement = connection.prepareStatement(baseSql);
//
//			// プリペアードステートメントに科目コード？をバインド
//			statement.setString(1, subject.getCd());		//訂正必要
//
//			// プライベートステートメントを実行
//			rSet = statement.executeQuery();
//
//			// リストへの格納処理を実行
//			list = postFilter(rSet);
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			// プリペアードステートメントを閉じる
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//			// コネクションを閉じる
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException sqle) {
//					throw sqle;
//				}
//			}
//		}
//		return list;
//	}
//
//}
