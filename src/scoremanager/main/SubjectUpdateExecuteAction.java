package scoremanager.main;

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

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ローカル変数の宣言 1
        SubjectDao sDao = new SubjectDao(); // SubjectDaoのインスタンスを生成
        HttpSession session = req.getSession(); // セッションを取得
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザーを取得
        ClassNumDao cNumDao = new ClassNumDao(); // ClassNumDaoのインスタンスを生成
        Map<String, String> errors = new HashMap<>(); // エラーメッセージを格納するマップを作成

        // リクエストパラメータの取得 2 すべてString型
        String cd = req.getParameter("cd"); // リクエストパラメータから科目コードを取得
        String name = req.getParameter("name"); // リクエストパラメータから科目名を取得

        // DBからデータ取得 3
        Subject subject = sDao.get(cd, teacher.getSchool()); // 科目コードと学校コードで科目を取得
        List<String> list = cNumDao.filter(teacher.getSchool()); // 学校コードでクラス番号の一覧を取得

        // ビジネスロジック 4
        // DBへデータ保存 5
        // 条件で4～5が分岐
        if (subject != null) { // 科目が存在する場合
            // インスタンスに値をセット
            subject.setName(name); // 科目名をセット
            subject.setCd(cd); // 科目コードをセット

            // 科目を保存
            sDao.save(subject); // 科目の情報を保存
        } else {
            errors.put("cd", "科目が存在していません"); // エラーメッセージを設定
        }

        // エラーがあったかどうかで手順6~7の内容が分岐
        // レスポンス値をセット 6
        // JSPへフォワード 7
        req.setAttribute("class_num_set", list); // クラス番号のリストをリクエスト属性にセット

        if (!errors.isEmpty()) { // エラーがあった場合、更新画面へ戻る
            // リクエスト属性をセット
            req.setAttribute("errors", errors); // エラーメッセージをセット
            req.setAttribute("cd", cd); // 科目コードをセット
            req.setAttribute("name", name); // 科目名をセット

            req.getRequestDispatcher("subject_update.jsp").forward(req, res); // 更新画面へフォワード
            return; // 処理を終了
        }
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res); // 完了画面へフォワード
    }

}
