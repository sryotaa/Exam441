<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>


	<h2>科目管理</h2>
	<a href="SubjectCreate.action">新規登録</a>


	<c:choose>

			<table class="table table-hover">
				<tr>
					<th>科目コード</th>
					<th>科目名</th>
					<th></th>
					<th></th>
				</tr>
				<c:forEach var="subject" items="${students}">
					<tr>
						<td>${subject.no}</td>
						<td>${subject.name}</td>
						<td class="text-center">

						</td>
						<td><a href="SubjectUpdate.action?no=${subject.no}">変更</a></td>
						<td><a href="SubjectDelete.action?no=${subject.no}">削除</a></td>
					</tr>
				</c:forEach>
			</table>
		<c:otherwise>
			<div>科目情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>


</body>
</html>