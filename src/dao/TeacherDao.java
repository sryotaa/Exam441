package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;

public class TeacherDao  extends Dao{


	/**
	 * getメソッド
	 *
	 */
	public Teacher get(String id) throws Exception {
		// 教員をインスタンス化
		Teacher teacher = new Teacher();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//学校Daoを初期化
		SchoolDao schoolDao = new SchoolDao();

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM TEACHER WHERE ID = ?");
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, id);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに教員IDとパスワードをセット
				teacher.setId(rSet.getString("id"));
				teacher.setPassword("password");
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
				} else {
				// 存在しない場合
				// 教員インスタンスにnullをセット
				teacher = null;
			}
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
		return teacher;
	}




	/**
	 * loginメソッド
	 *
	 */
	public Teacher login(String id, String password) throws Exception {
		// 教員をインスタンス化
		Teacher teacher = new Teacher();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM TEACHER WHERE ID = ? AND PASSWORD = ?");
			//プリペアードステートメントに教員IDをバインド
			statement.setString(1, id);
			statement.setString(2, password);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//教員インスタンスに検索結果をセット
				teacher.setId(rSet.getString("id"));
				teacher.setPassword(rSet.getString("password"));
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				//リザルトセットが存在しない場合
				//教員インスタンスにnillをセット
				teacher = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

		}

		return teacher;
	}
}
