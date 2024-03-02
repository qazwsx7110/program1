<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%> 

<head>
<meta charset="UTF-8">
 <script src="//code.jquery.com/jquery-3.3.1.js"></script> 
<script type="text/javascript">

//글목록 돌아가기
 function backToList(obj){
 obj.action="${contextPath}/board/listArticles.do";
 obj.submit();
 }
     
     //제출
  function s() {
		//제목 확인
	   if(document.frmReply.title.value == ""){					   
			    alert("제목을 적어주세요.");
			    return false;
		    }	   
				    		
	    document.frmReply.submit();
		
 }	
</script> 
<title>답글쓰기 페이지</title>
</head>

<body>
 <h1>답글쓰기</h1>
  <form name="frmReply" method="post"  action="${contextPath}/board/addReply.do"   enctype="multipart/form-data">
    <table border="0" align="center">
    <tr>
			<td align="right"> 작성자:&nbsp; </td>
			<td colspan=2 ><input type="text" size="20" maxlength="100"  name="writer" value="${member.name }" readonly></input> </td>
		</tr>
		<tr>
			<td align="right">제목:&nbsp;  </td>
			<td><input type="text" size="67"  maxlength="500" name="title" id="title"> </input></td>
		</tr>
		<tr>		
			<td align="right" valign="top"><br>내용:&nbsp; </td>
			<td><textarea name="content" rows="10" cols="65" maxlength="4000"> </textarea> </td>
		</tr>						
		<tr>			
			<td align="right"> </td>
			<td>
				<input type=button value="답글쓰기" onClick="s()"/>
				<input type=button value="취소"onClick="backToList(this.form)" />
				
			</td>
		</tr>
    
    </table>
  
  </form>
</body>
</html>