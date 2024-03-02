<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
   request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<%@ page import="com.myspring.pro30.member.dao.MemberDAO"%>
<%@ page import="com.myspring.pro30.member.dao.MemberDAOImpl"%>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입창</title>

<style>
   .text_center{
     text-align:center;
   }
   
.id_ok{
color:#008000;
display: none;
}

.id_already{
color:#6A82FB; 
display: none;
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



    if(document.memberForm.idcheck.value == "0"){
	    alert("아이디를 중복 체크 해주세요.");
	    return false;
    }
	else{
		if(document.memberForm.id.value != document.memberForm.cid.value){
		alert("아이디를 다시 체크해주세요.");
		return false;
		}
		else{
			
		}
	}
	
    if(document.memberForm.pw.value == ""){
    	alert("비밀번호을 적어주세요.");
    	return false;
    }
	
    if(document.memberForm.namecheck.value == "0"){
	    alert("이름을 중복체크해주세요.");
	    return false;
    }
	else{
		if(document.memberForm.name.value != document.memberForm.cname.value){
		alert("이름을 다시 체크해주세요.");
		return false;
		}
		else{
			
		}
	}
    
    if(document.memberForm.email.value == ""){
    	alert("이메일을 적어 주세요.");
    	return false;
    }
    
    if(document.memberForm.account.value == ""){
    	alert("권한을 선택해주세요.");
    	return false;
    }
    
    document.memberForm.submit();
}	

//아이디 중복체크
function duplicationId() {
	var id = $('#id').val();
	$.ajax({
        url:'${pageContext.request.contextPath}/member/idCheck.do', //Controller에서 요청 받을 주소
        type:'post', //POST 방식으로 전달
        data:{id:id}, 
        
        success:function(cnt){ //컨트롤러에서 넘어온 cnt값을 받는다 
        	
        	 if(cnt == 0){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디 
                 $('.id_ok').css("display","inline-block"); 
                 $('.id_already').css("display", "none");
                 $('#cid').val(id);
                 $('#idcheck').val('1');
                 
             } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                 $('.id_already').css("display","inline-block");
                 $('.id_ok').css("display", "none");                
                 $('#id').val('');
                                  
                 $('#idcheck').val('0');
             }
        	
            
        },
        error:function(){
            alert(cnt+"에러입니다");
        }
    });
}

//이름 중복 체크
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

function checkidreset(){
	
	$('.id_already').css("display", "none");
	$('.id_ok').css("display", "none"); 
	
	$('#idcheck').val('0');
}

function checkreset(){
	$('.id_already').css("display", "none");
	$('.id_ok').css("display", "none"); 
	$('.name_already').css("display", "none");
	$('.name_ok').css("display", "none"); 
}

</script>
<body>
	<form method="post" name="memberForm"  action="${contextPath}/member/addMember.do" enctype="multipart/form-data">
	<h1  class="text_center">회원 가입창</h1>
	<table  align="center">
	   <tr>
	      <td width="200"><p align="right">아이디</td>
	      <td width="400"><input type="text" name="id" id = "id" value = "" onchange="checkidreset()"></td>
	      <td width="50"><input type=button value="중복확인" onClick="duplicationId()" /></td>
	      <input  type= "hidden"   name="cid" id="cid" value="" />
	      <input  type= "hidden"   name="idcheck" id="idcheck" value="0" />	      
	   </tr>
	   <tr>
	      <td width="200"></td>
	      <td><span class="id_ok">사용 가능한 아이디입니다.</span>
          <span class="id_already">누군가 이 아이디를 사용하고 있어요.</span></td>
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
	       <td width="400"><p><input type="text" name="email" id="email"></td>
	    </tr>
	    <tr>
	       <td width="200"><p align="right">권한</td>
	       <td width="400"><input type="radio" name="account" id="account"  value="1" />관리자<input type="radio" name="account" id="account" value="2" />일반</td>
	    </tr>
	    <tr>
	       <td width="200"><p>&nbsp;</p></td>
	       <td width="400"><input type=button value="가입하기" onClick="s()"><input type="reset" value="다시입력" onClick="checkreset()"></td>
	    </tr>	       	    
	</table>
	</form>
</body>
