
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	private String baseSql = "select * from subject where school_cd=? ";

//ゲット

	public Subject get(String cd)throws Exception{

		Subject subject=new Subject();

		Connection connection=getConnection();

		PreparedStatement statement=null;

		try{   //科目コード別に作成

			statement=connection.prepareStatement("select * from subject where cd=?");

			statement.setString(1, cd);

			ResultSet rSet=statement.executeQuery();

			SchoolDao schoolDao=new SchoolDao();

			if (rSet.next()) {

				subject.setCd(rSet.getString("cd"));

				subject.setName(rSet.getString("name"));

				//学校フィールドには学校コードで検索した学校インスタンスをセット

				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));

			} else {

				//リザルトセットが存在しない場合

				//学生インスタンスにnullをセット

				subject= null;

			} }catch (Exception e) {

				throw e;

			} finally {

				if(statement != null) {

					try {

						statement.close();

					} catch (SQLException sqle) {

						throw sqle;

					}

				}

			}

		return subject;

	}




	public List<Subject> postFilter(ResultSet rSet,School school) throws Exception {

		//戻り値用のリスト

		List<Subject> list = new ArrayList<>();

		try{

			while(rSet.next()) {

				//学生インスタンスを初期化

				Subject subject = new Subject();

				//学生インスタンスに検索結果をセット

				subject.setCd(rSet. getString("cd"));

				subject.setName (rSet. getString("name"));

				subject.setSchool (school);

				//リストに追加

				list.add(subject);

			}

		} catch (SQLException | NullPointerException e) {

			e.printStackTrace();

		}

		return list;

	}

	//フィルター（出力）

	public List<Subject> filter(School school)throws Exception {

		//リストを初期化

	    List<Subject> list = new ArrayList<>();

	    //コネクションを確立

	    Connection connection = getConnection();

	    //プリペアードステートメント

	    PreparedStatement statement = null;

	    //リザルトセット

	    ResultSet rSet = null;

	    //SQL文の条件

//	    String condition = "and ent_year =? and class_num =? ";

	    try {

		    //プリペアードステートメントにSQL文をセット

		    statement = connection. prepareStatement (baseSql);

		    //プリペアードステートメントに学校コードをバインド

		    statement. setString(1, school. getCd ());

		    // プライベートステートメントを実行

		    rSet = statement.executeQuery ();

		    //帰ってきたResultSet型をStudent型に変えて結果をセットする

		    list = postFilter(rSet,school);

		} catch (Exception e) {

			throw e;

		} finally {

			//

			if(statement != null) {

				try {

					statement.close();

				} catch (SQLException sqle) {

					throw sqle;

				}

			}

			if(connection != null) {

				try {

					connection.close();

				} catch (SQLException sqle) {

					throw sqle;

				}

			}

		}

		//listを返す

		return list;

	}



	//セーブ（更新＆作成用）

	public boolean save(Subject subject) throws Exception {

		//コネクションを確立

		Connection connection = getConnection();

		//プリペアードステートメント

		PreparedStatement statement = null;

		//実行件数

		int count = 0;

		try{

			//データベースから学生を取得

			Subject old = get(subject.getCd());

			if (old == null) {

				//学生が存在しなかった場合

				//プリペアードステートメンにINSERT文をセット

				statement = connection.prepareStatement(

						"insert into subject (school_cd, cd, name,) values(?, ?, ?,) ");

				//プリペアードステートメントに値をバインド

				statement.setString(1, subject.getSchool().getCd());

				statement.setString(2,subject.getCd());

				statement.setString(3,subject.getName());

			} else {

				//学生が存在した場合

				//プリペアードステートメントにUPDATE文をセット

				statement = connection

						.prepareStatement("update subject set name=? where cd=?");

				//プリペアードステートメントに値をバインド


				statement.setString(1,subject.getName());

				statement.setString(2,subject.getCd());

			}

			//プリペアードステートメントを実行

			count = statement.executeUpdate();

		} catch (Exception e) {

			throw e;

		} finally {

			//

			if(statement != null) {

				try {

					statement.close();

				} catch (SQLException sqle) {

					throw sqle;

				}

			}

			if(connection != null) {

				try {

					connection.close();

				} catch (SQLException sqle) {

					throw sqle;

				}

			}

		}

		if (count > 0) {

			//実行件数が1以上ある場合

			return true;

		} else {

			//実行件数が0件の場合

			return false;

		}




	}}
