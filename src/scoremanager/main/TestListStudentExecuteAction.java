package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー


		String studentNoStr="";// 入力された学生番号
//		int studentNo=0;

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

		List<TestListStudent> tlsstudents = null;// 学生別成績一覧リストを初期化
		TestListStudentDao tlsDao = new TestListStudentDao();//学生別成績一覧Daoを初期化

		Student student = null;// 学生リストを初期化
		StudentDao sDao = new StudentDao();//学生Daoを初期化

		SubjectDao subDao = new SubjectDao();//科目Daoを初期化

		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化

		Map<String, String> errors = new HashMap<>();// エラーメッセージ


		//リクエストパラメータ―の取得 2
		studentNoStr = req.getParameter("studentNo");

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());
//		科目一覧を取得
		List<Subject> sublist = subDao.filter(teacher.getSchool());


		 String stu_name="";


		try{
//			学生情報を取得
			student = sDao.get(studentNoStr);
			tlsstudents = tlsDao.filter(student);

			stu_name=student.getName();
		}catch(NullPointerException e){
			System.out.print(student);//null

			errors.put("nullpo", "学生番号が存在しませんでした");
		}



		//DBからデータ取得 3


//		if (studentNoStr != null) {
//			// 数値に変換
//			studentNo = Integer.parseInt(studentNoStr);
//		}


//		 System.out.print(tlsstudents.size());



			List<Integer> entYearSet = new ArrayList<>();

			for (int i = year - 10; i < year + 1; i++) {
				entYearSet.add(i);
			}// 現在を起点に前後10年をリストに追加


//		List<TestListStudent> tlslist = tlsDao.filter(test.getStudent());


		// リクエストにデータをセット
		req.setAttribute("tlsstudents",tlsstudents);
		req.setAttribute("stu_name", stu_name);

		req.setAttribute("errors", errors);


		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", list);
		req.setAttribute("subject_set", sublist);


		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);




	}
}
