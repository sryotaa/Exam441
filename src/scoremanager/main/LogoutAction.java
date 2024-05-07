package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		//なし
		//リクエストパラメータ―の取得 2
		//なし
		//DBからデータ取得 3
		//なし
		//ビジネスロジック 4

		//Sessionが存在するか確認	→	存在する＝現在のセッションを取得(◇１)
		//								存在しない＝nullを返す(◆２)
		HttpSession session = req.getSession(false);

		if (session != null) {
			//Sessionが存在する場合(◇１)
			System.out.println("セッションの存在を確認しました。ログアウトします。");
			session.invalidate();	//Sessionを削除(テキストｐ269)
		} else {
			//Sessionが存在しなかった場合(◆２)
			System.out.println("セッションは存在しない為、ログインできません。");
		}

		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		//なし
		//JSPへフォワード 7
		req.getRequestDispatcher("logout.jsp").forward(req, res);
	}
}
