package scoremanager.main;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String entYearStr = "";
		String classNum = "";
		String subjectCd ="";
		int entYear=0;

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

		List<TestListSubject> tlssubjects = null;// 科目別成績一覧リストを初期化
		TestListSubjectDao tlsubDao = new TestListSubjectDao();//科目別成績一覧Daoを初期化

		Subject subject = null;// 科目リストを初期化
		SubjectDao subDao = new SubjectDao();//科目Daoを初期化


		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス
		subjectCd = req.getParameter("f3");//科目コード



		System.out.print(entYearStr);
		System.out.print(classNum);
		System.out.print(subjectCd);





		subject = subDao.get(subjectCd);

		//DBからデータ取得 3
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}


		tlssubjects = tlsubDao.filter(entYear, classNum, subject, teacher.getSchool());



		// リクエストにデータをセット
		req.setAttribute("tlssubjects",tlssubjects);

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);



	}

}
