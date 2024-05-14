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
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String subjectStr="";// 入力された科目コード
		String numStr="";// 入力されたテスト回数
		int num = 0;//回数
		int entYear = 0;// 入学年度
		List<Test> tests = null;// テストリスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao subDao = new SubjectDao();// 科目Daoを初期化
		TestDao tDao = new TestDao();// テストDaoを初期化
		Subject subject = new Subject();// 科目Daoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");
		numStr = req.getParameter("f4");

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号と科目の一覧を取得
		List<String> clist = cNumDao.filter(teacher.getSchool());
		List<Subject> slist = subDao.filter(teacher.getSchool());

		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		if (numStr != null) {
			// 数値に変換
			num = Integer.parseInt(numStr);
		}

		if (subjectStr != null && !subjectStr.equals("0")) {

			subject.setCd(subjectStr);
		}

		if (entYear != 0 && !classNum.equals("0") && !subjectStr.equals("0") && !subjectStr.equals(null) && num != 0) {
			// 入学年度、クラス番号、科目、回数を指定
			tests = tDao.filter(entYear, classNum, subject , num, teacher.getSchool());
		} else if (subjectStr == null){
			//科目コードがnullの時(検索画面の最初の時)エラーメッセージを空にする
			errors.put("f1", "");
			req.setAttribute("errors", errors);
		}else {
			//検索条件を入れなかった時のエラーメッセージ
			errors.put("f1", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
		}

		//ビジネスロジック 4

		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		// リクエストに科目をセット

		//科目コードと科目名をset
		if (subjectStr != null && !subjectStr.equals("0")){
		subject = subDao.get(subjectStr, teacher.getSchool());
		String subjectName = subject.getName();
		req.setAttribute("f3", subjectStr);//科目コード
		req.setAttribute("subject_name", subjectName);//科目名
		}

		req.setAttribute("f4", num);//回数

		// リクエストにテストリストをセット
		req.setAttribute("tests", tests);
		// リクエストにデータをセット
		req.setAttribute("class_num_set", clist);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", slist);
		req.setAttribute("ent_year", entYear);
		//JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

// 設計書に書いてあったけど使えなかった
//	private void setRequestData(HttpServletRequest req, HttpServletResponse res) {
//
//	}

}
