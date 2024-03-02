<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>    


<html>
<head>
<meta charset=UTF-8">
<title>회원 정보</title>
<style>
   .text_center{
     text-align:center;
   }
</style>
</head>
<body>

<form method="post"   action="${contextPath}/member/removeMember.do?id=${member.id }">
	<table  align="center">
	  
	   <tr>
	      <td width="200"><p align="right">아이디</td>
	      <td width="400">${member.id}</td>
	    </tr>	   	  
	    <tr>
	       <td width="200"><p align="right">이름</td>
	       <td width="400">${member.name}</td>
	    </tr>
	    <tr>
	       <td width="200"><p align="right">이메일</td>
	       <td width="400">${member.email}</td>
	    </tr>
	    <tr>
	       <td width="200"><p align="right">가입일</td>
	       <td width="400">${member.joinDate}</td>
	    </tr>	    
	    <tr>
	       <td width="200"><p>&nbsp;</p></td>
	       <td width="400"><input type="submit" value="회원탈퇴">
	    </tr>
	</table>
	</form>

</body>
</html>
