<%-- 学生更新完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="./common/header.jsp"/>

<div class="tmain">

<c:import url="./common/navigation.jsp"/>
	<h2>学生情報変更</h2>
		<p>変更が完了しました</p>
	<a href="StudentList.action">学生一覧</a>
</div>

<c:import url="./common/footer.jsp"/>
</html>

