package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String subjectStr="";// 入力された科目
		String numStr="";// 入力された回数
		String pointStr="";// 入力された得点
		int num = 0;//回数
		int point = 0;//得点
		int entYear = 0;// 入学年度
		List<Test> test = null;// 学生リスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		TestListStudentDao tlsDao = new TestListStudentDao();
		SubjectDao sDao = new SubjectDao();// 科目Daoを初期化
		TestDao tDao = new TestDao();// テストDaoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> clist = cNumDao.filter(teacher.getSchool());
		List<Subject> slist = sDao.filter(teacher.getSchool());

		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		if (subjectStr != null) {
			// 数値に変換
			Subject subject = subjectStr;
		}

		if (numStr != null) {
			// 数値に変換
			num = Integer.parseInt(numStr);
		}

		if (pointStr != null) {
			// 数値に変換
			point = Integer.parseInt(pointStr);
		}

		if (entYear != 0 && !classNum.equals("0") && num != 0) {
			// 入学年度、クラス番号、回数を指定
			test = tDao.filter(entYear, classNum, tlsDao.getSubject() , num, teacher.getSchool());
		} else {
			errors.put("f1", "指定してください");
			req.setAttribute("errors", errors);
		}

	}





	private void setRequestData(HttpServletRequest req, HttpServletResponse res) {

	}
}
