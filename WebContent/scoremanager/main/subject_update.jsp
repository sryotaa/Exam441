<%-- 学生更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="./common/header.jsp"/>

<div class="tmain">

<c:import url="./common/navigation.jsp"/>
	<h2>科目情報変更</h2>
	<form action = "SubjectUpdateExecute.action" method="post">

	<p><label>科目コード</label><br>
		<input type="hidden" name="cd" value="${cd}">
			${cd}</p>


		<label>科目名</label>
		<input type="text" name="name"
			maxlength="10" value="${name}" required />
		<div>${errors.get("name")}</div>



		<input type="submit" value="変更">

	</form>

	<a href="SubjectList.action">戻る</a>

</div>

<c:import url="./common/footer.jsp"/>
</html>