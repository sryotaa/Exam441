package scoremanager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		String url = "";

		Teacher teacher = new Teacher();
		TeacherDao teacherDAO = new TeacherDao();

		//リクエストパラメータ―の取得 2
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		//DBからデータ取得 3
		//☆教員IDとパスワードから教員インスタンスを取得
		teacher = teacherDAO.login(id, password);

		//ビジネスロジック 4
		//☆
		if (teacher != null) {
			//認証成功

			//Sessionを有効にする
			HttpSession session = req.getSession(true);
			// 認証済みフラグを立てる
			teacher.setAuthenticated(true);
			//セッションに"user"という変数名で値はTeacher変数の中身
			session.setAttribute("user", teacher);

			//リダイレクト
			url = "main/Menu.action";
			res.sendRedirect(url);
		} else {
			//認証失敗
			 List<String> errors = new ArrayList<>();
				errors.add("ログインに失敗しました。IDまたはパスワードが正しくありません。");
				req.setAttribute("errors", errors);

			//JSPへフォワード
			url = "login.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		}
		//req.getRequestDispatcher("main/Menu.action").forward(req, res);

	}

}
