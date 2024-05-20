<%-- 科目登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:import url="./common/header.jsp"/>

<div class="tmain">

<c:import url="./common/navigation.jsp"/>
	<h2>科目情報登録</h2>
		<p>登録が完了しました</p>
	<a href="SubjectCreate.action">戻る</a>
	<a href="SubjectList.action">科目一覧</a>
</div>

<c:import url="./common/footer.jsp"/>
</html>