<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정창</title>
<style>
   .text_center{
     text-align:center;
   }
   
 .name_ok{
color:#008000;
display: none;
}

.name_already{
color:#6A82FB; 
display: none;
} 
</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<script>
function s() {

	var id = document.getElementById("id");
	var pw = document.getElementById("pw");
	var name = document.getElementById("name");
	var email = document.getElementById("email");

   if(document.modmember.name.value != ""){
		
	   if(document.modmember.namecheck.value == "0"){
		    alert("이름을 중복체크해주세요.");
		    return false;
	    }	   
		
    }
    else{
    	$('#name').val(document.modmember.name.value);
	}
	
	
	if(document.modmember.pw.value == ""){
		
		$('#pw').val(document.modmember.opw.value);
    }				
	
    if(document.modmember.email.value == ""){
		
		$('#email').val(document.modmember.oemail.value);
    }
	
    document.modmember.submit();
	}	
	
	//이름 중복체크
function duplicationName() {
	var name = $('#name').val();
	$.ajax({
        url:'${pageContext.request.contextPath}/member/nameCheck.do', //Controller에서 요청 받을 주소
        type:'post', //POST 방식으로 전달
        data:{name:name}, 
        
        success:function(cnt){ //컨트롤러에서 넘어온 cnt값을 받는다 
        	
        	 if(cnt == 0){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디 
                 $('.name_ok').css("display","inline-block"); 
                 $('.name_already').css("display", "none");
                 $('#cname').val(name);
                 $('#namecheck').val('1');
                 
             } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                 $('.name_already').css("display","inline-block");
                 $('.name_ok').css("display", "none");                
                 $('#name').val('');
                 
                 $('#namecheck').val('0');
             }        	            
        },
        error:function(){
            alert(cnt+"에러입니다");
        }
    });
}

function checknamereset(){	
	$('.name_already').css("display", "none");
	$('.name_ok').css("display", "none"); 	
	$('#namecheck').val('0');
}

function checkreset(){	
	$('.name_already').css("display", "none");
	$('.name_ok').css("display", "none"); 
}
</script>
<body>
	<form method="post" name="modmember"   action="${contextPath}/member/mod.do">
	<h1  class="text_center">회원 정보 수정창</h1>
	<table  align="center">
	   <tr>	   
	     <input  type= "hidden"   name="id" value="${member.id}" />
	     <input  type= "hidden"   name="account" value="${member.account}" />
	     <input  type= "hidden"   name="joinDate" value="${member.joinDate}" />	 
	     
	     <input  type= "hidden"   id="opw" value="${member.pwd}" />
	     <input  type= "hidden"   id="oname" value="${member.name}" />
	     <input  type= "hidden"   id="oemail" value="${member.email}" />	 
	   </tr>
	   <tr>
	      <td width="200"><p align="right">비밀번호</td>
	      <td width="400"><input type="password" name="pwd" id = "pw"></td>
	    </tr>
	    <tr>
	       <td width="200"><p align="right">이름</td>
	       <td width="400"><p><input type="text" name="name" id = "name" value = "" onchange="checknamereset()"></td>
	       <td width="50"><input type=button value="중복확인"onClick="duplicationName()" /></td>
	       <input  type= "hidden"   name="cname" id="cname" value="" />
	       <input  type= "hidden"   name="namecheck" id="namecheck" value="0" />
	    </tr>
	     <tr>
	      <td width="200"></td>
	      <td><span class="name_ok">사용 가능한 이름입니다.</span>
          <span class="name_already">누군가 이 이름를 사용하고 있어요.</span></td>
	   </tr>
	    <tr>
	       <td width="200"><p align="right">이메일</td>
	       <td width="400"><p><input type="text" name="email" id = "email"></td>
	    </tr>
	    <tr>
	       <td width="200"><p>&nbsp;</p></td>
	       <td width="400"><input type=button value="수정하기" onClick="s()"><input type="reset" value="다시입력" onClick="checkreset()"></td>
	    </tr>
	</table>
	</form>
</body>
</html>