package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
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

		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化


		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス
		subjectCd = req.getParameter("f3");//科目コード



		subject = subDao.get(subjectCd);

		//DBからデータ取得 3

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());
//		科目一覧を取得
		List<Subject> sublist = subDao.filter(teacher.getSchool());


		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}


		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}// 現在を起点に前後10年をリストに追加



		tlssubjects = tlsubDao.filter(entYear, classNum, subject, teacher.getSchool());

		 String sub_name=subject.getName();



		// リクエストにデータをセット
			// リクエストに入学年度をセット
			req.setAttribute("f1", entYear);
			// リクエストにクラス番号をセット
			req.setAttribute("f2", classNum);
			// リクエストに科目をセット
			req.setAttribute("f3", subjectCd);


		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", list);
		req.setAttribute("subject_set", sublist);

		req.setAttribute("sub_name",sub_name);

		req.setAttribute("tlssubjects",tlssubjects);

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);



	}

}
