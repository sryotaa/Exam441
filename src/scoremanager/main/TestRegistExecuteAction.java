package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		TestDao tDao = new TestDao();//テストDaoを初期化
		int entYear = 0;//入学年度
		String subjectCd = "";//科目
		String classNum = "";//クラス番号
		int no = 0;//回数
		String pointStr;//得点
		SubjectDao subDao = new SubjectDao();
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		List<Test> tlist = new ArrayList<>(); //テスト情報を格納するリストを初期化(得点を代入する前)
		List<Test> lists = new ArrayList<>(); //テスト情報を格納するリストを初期化(得点を代入した後)
//		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//リクエストパラメータ―の取得 2
		entYear = Integer.parseInt(req.getParameter("f1"));//入学年度
		classNum = req.getParameter("f2");//クラス番号
		subjectCd = req.getParameter("f3");//科目コード
		no = Integer.parseInt(req.getParameter("f4"));//回数
		Subject subject = subDao.get(subjectCd, teacher.getSchool());//科目

		//検索条件に一致するテスト情報を取得
		tlist = tDao.filter(entYear, classNum, subject, no, teacher.getSchool());

		// 	入力された得点を取得
		for(Test test : tlist) {
			pointStr = req.getParameter("point_" + test.getStudent().getNo());
			if(pointStr != ""){
				// 得点がnullじゃない時得点をtestにset
				test.setPoint(Integer.parseInt(pointStr));
			}else{
				// 得点に何も入れなかったとき-1を代入
				test.setPoint(-1);
			}
			lists.add(test);
		}

		// テストをデータベースに保存
		tDao.save(lists);

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}
