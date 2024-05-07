<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>

	<h2>成績一覧（学生）</h2>

	<form method="get">

		<label>学生番号</label>


			<input type="text" name="f4">



		<!--			  	<select name="f4">
		<option value="0">--------</option>
			<c:forEach var="no" items="${studentNo}">
				<%-- 現在のnoと選択されていたf4が一致していた場合selectedを追記 --%>
				<option value="${no}" <c:if test="${no==f4}">selected</c:if>>${no}</option>
			</c:forEach>

					</select>
				-->


		<button>検索</button>

		<div>${errors.get("f1")}</div>
	</form>

	<c:choose>
		<c:when test="${tlsstudents.size()>0}">
			<div>学生番号()</div>

			<table class="table table-hover">
				<tr>
					<th>科目名</th>
					<th>科目コード</th>
					<th>回数</th>
					<th>点数</th>
				</tr>
				<c:forEach var="tlsstudents" items="${tlsstudents}">
					<tr>
						<td>${testliststudent.subjectName}</td>
						<td>${testliststudent.subjectId}</td>
						<td>${testliststudent.num}</td>
						<td>${testliststudent.point}</td>

					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<div>学生情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>

</body>
</html>