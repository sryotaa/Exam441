package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String studentNoStr="";// 入力された学生番号
		int studentNo=0;
		List<TestListStudent> tlsstudents = null;// 学生リスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		TestListStudentDao tlsDao = new TestListStudentDao();//学生別成績一覧Dao
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ


		//リクエストパラメータ―の取得 2
		studentNoStr = req.getParameter("f1");

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

			//ここに昨日作成した部分をコピペしてみよう！！
		if (studentNoStr != null) {
			// 数値に変換
			studentNo = Integer.parseInt(studentNoStr);
		}

		if (studentNo != 0) {
			// 学生番号を指定
			tlsstudents = tlsDao.filter(.get());//わからん
		} else {
			errors.put("f1", "指定してください");
			req.setAttribute("errors", errors);
		}

		//レスポンス値をセット 6
		// リクエストに学生番号をセット
		req.setAttribute("f1", studentNo);


		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);



	}

}
