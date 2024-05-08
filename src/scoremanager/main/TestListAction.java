package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー


		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String subject="";//入力された科目
		int entYear=0;//入学年度


		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

		SubjectDao subDao = new SubjectDao();
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ


		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subject = req.getParameter("f3");

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

		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}// 現在を起点に前後10年をリストに追加


		//レスポンス値をセット 6




		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		// リクエストに科目をセット
		req.setAttribute("f3", subject);



//		System.out.print(classNum);

		// リクエストにリストをセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", list);
		req.setAttribute("subject_set", sublist);




		//JSPへフォワード 7
		req.getRequestDispatcher("test_list.jsp").forward(req, res);



	}

//	private void setTestListStudent(HttpServletRequest req, HttpServletResponse res) {
//
//	}


}
