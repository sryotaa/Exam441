<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<%-- ヘッダー --%>
<c:import url="./common/header.jsp"/>

<div class="tmain">
<%-- ナビゲーション --%>
<c:import url="./common/navigation.jsp"/>


	<h2>成績登録</h2>

	<form method="get">
		<label>入学年度 </label>
		<select name="f1">
			<option value="0">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}"  <c:if test="${year==f1}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>


		<label>クラス</label>
		<select name="f2">
			<option value="0">--------</option>
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>


		<label>科目</label>
		<select name="f3">
			<option value="0">--------</option>
			<c:forEach var="subject" items="${subject_set}">
				<%-- 現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
				<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
			</c:forEach>
		</select>


		<label>回数</label>
		<select name="f4">
			<option value="0">--------</option>
			<option value="1"<c:if test="${1==f4}">selected</c:if>>1</option>
			<option value="2"<c:if test="${2==f4}">selected</c:if>>2</option>
		</select>


		<button>検索</button>
		<!-- 検索条件を入れなかったときのエラーメッセージを表示 -->
		<div>${errors.get("f1")}</div>
	</form>

	<c:choose>
		<c:when test="${tests.size()>0}">
			<div>科目：${subject_name}(${f4}回)</div>

			<table class="table table-hover">

				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th>得点</th>
				</tr>

				<c:forEach var="test" items="${tests}">

					<tr>
						<td>${test.student.entYear}</td>
						<td>${test.classNum}</td>
						<td>${test.student.no}</td>
						<td>${test.student.name}</td>
				<form action = "TestRegistExecute.action" method="post">
						<!-- test.pointが-1(nunll)の時valueを空にする -->
						<c:if test="${test.point != -1}">
						<td><input type="number" name="point_${test.student.no }" min="0" max="100" value="${test.point }"></td>
						<div>${errors.get("point")}</div>
						</c:if>
						<c:if test="${test.point == -1}">
						<td><input type="number" name="point_${test.student.no }" min="0" max="100" value=""></td>
						<div>${errors.get("point")}</div>
						</c:if>

					</tr>
				</c:forEach>

			</table>

				<input type="hidden" name="f1" value="${f1}">
				<input type="hidden" name="f2" value="${f2}">
				<input type="hidden" name="f3" value="${f3}">
				<input type="hidden" name="f4" value="${f4}">
				<input type="submit" value="登録をして終了">
			</form>
				<a href="menu.jsp">戻る</a>

		</c:when>
		<c:when test="${ent_year == 0 or f2 == null or f2 == 0 or subject_name == null or f4 == 0}">
		<!-- testのリストが空かつ検索条件が入っていない時 -->
		</c:when>
		<c:otherwise>
			<div>テストの情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>
</div>
<%-- フッター --%>
<c:import url="./common/footer.jsp"/>
</html>
