<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div class="menu">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
<%-- ヘッダー --%>
<c:import url="./common/header.jsp"/>


<div class="main">
<%-- ナビゲーション --%>
<c:import url="./common/navigation.jsp"/>

<div class="content">
<h2 class="menu_title">メニュー</h2>

<div class="block1"><a href="StudentList.action">学生管理</a></div>
<div class="block2"><a href="TestRegist.action">成績登録</a>
<a href="TestList.action">成績参照</a></div>
<div class="block3"><a href="SubjectList.action">科目管理</a></div>


</div>
</div>
</body>

<%-- フッター --%>
<c:import url="./common/footer.jsp"/>
</div>
</html>
