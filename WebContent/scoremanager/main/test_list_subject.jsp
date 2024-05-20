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

<c:import url="./common/header.jsp"/>

<div class="tmain">

<c:import url="./common/navigation.jsp"/>

	<h2>成績一覧（科目）</h2>

	<form action = "TestListSubjectExecute.action" method="post">
		<label>入学年度</label>
		<select name="f1" required>
			<option value="">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>

		<label>クラス</label>
		<select name="f2" required>
			<option value="">--------</option>
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<label>科目</label>
		<select name="f3" required>
			<option value="">--------</option>
			<c:forEach var="sub" items="${subject_set}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${sub.cd}" <c:if test="${sub.getCd()==f3}">selected</c:if>>${sub.name}</option>
			</c:forEach>
		</select>


		<button>検索</button>

		<div>${errors.get("f1")}</div>
	</form>


	<form action = "TestListStudentExecute.action" method="post">

		<label>学生番号</label>

				<input type="text" name="studentNo" required>


		<button>検索</button>

		<div>${errors.get("f1")}</div>
	</form>



	<c:choose>
		<c:when test="${tlssubjects.size()>0}">
					<div>科目： ${sub_name}</div>


			<table>
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th class="text-center">1回</th>
					<th class="text-center">2回</th>

				</tr>
				<c:forEach var="testlistsubject" items="${tlssubjects}">
					<tr>
						<td>${testlistsubject.entYear}</td>
						<td>${testlistsubject.classNum}</td>
						<td>${testlistsubject.studentNo}</td>
						<td>${testlistsubject.studentName}</td>
						<td>${testlistsubject.getPoint(1)}</td>
						<td>${testlistsubject.getPoint(2)}</td>

					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<div>学生情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>


</div>

<c:import url="./common/footer.jsp"/>

</html>