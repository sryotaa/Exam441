package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー


		String studentNoStr="";// 入力された学生番号
		int studentNo=0;

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

		List<TestListStudent> tlsstudents = null;// 学生別成績一覧リストを初期化
		TestListStudentDao tlsDao = new TestListStudentDao();//学生別成績一覧Daoを初期化

		Student student = null;// 学生リストを初期化
		StudentDao sDao = new StudentDao();//学生Daoを初期化

		Test test = new Test();


		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		TestDao tDao = new TestDao();

		//リクエストパラメータ―の取得 2
		studentNoStr = req.getParameter("studentNo");

		student = sDao.get(studentNoStr);




		//DBからデータ取得 3

		if (studentNoStr != null) {
			// 数値に変換
			studentNo = Integer.parseInt(studentNoStr);
		}

		tlsstudents = tlsDao.filter(student);


//		List<TestListStudent> tlslist = tlsDao.filter(test.getStudent());


		// リクエストにデータをセット
		req.setAttribute("tlsstudents",tlsstudents);




		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);




	}
}