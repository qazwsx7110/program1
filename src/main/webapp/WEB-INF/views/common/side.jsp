<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>

<html>
<head>
 <style>
   .no-underline{
      text-decoration:none;
   }
 </style>
  <meta charset="UTF-8">
  <title>사이드 메뉴</title>
</head>
<body>
	<h1>사이드 메뉴</h1>
	 
	<h1>
	    <c:if test="${isLogOn == true  && member.account == 1}">
		<a href="${contextPath}/member/listMembers.do"  class="no-underline">회원관리</a><br>
		</c:if>
		<c:if test="${isLogOn == true}">
		<a href="${contextPath}/member/modMember.do"  class="no-underline">회원정보수정</a><br>
		<a href="${contextPath}/member/infomationMember.do"  class="no-underline">회원정보</a><br>
		</c:if>		
		<a href="${contextPath}/board/listArticles.do"  class="no-underline">게시판</a><br>
		
	</h1>
	
</body>
</html>