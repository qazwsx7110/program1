/**
 * 
 */


function fn_addoption(){
	 const optionDiv = document.createElement('div');
	optionDiv.innerHTML =`
	
	<div class="option_input">                          
                            
              옵션이름<input type="text" name="option" ;/>가격<input type="text" name="oprice";/>
              <input type="button" value="삭제" onClick="fn_remove(this)">
     </div>     
	`;		
	  document.querySelector('.option_list').appendChild(optionDiv);
	 optioncnt++;
  }  
 
   function fn_remove(element){
	element.parentElement.remove();
  }  
  
  
   function readURL(input) {
 if (input.files && input.files[0]) {
  var reader = new FileReader();
  
  reader.onload = function (e) {
	console.log(input.parentElement.querySelector('img'));
   input.parentElement.querySelector('img').setAttribute('src', e.target.result);  
  }
  
  reader.readAsDataURL(input.files[0]);
  }
}

 function viewFile(e) {
	readURL(e);
	
	
 }
 
 
 
  function fn_addFile(){
	 const fileDiv = document.createElement('div');
	fileDiv.innerHTML =`	
	<div class="simage">    
	<input type="button" value="업" onClick="fn_up(this)"><input type="button" value="다운" onClick="fn_down(this)">                        
              <input type="hidden" name="changeimg" value="false">
              <img name="file" />                
              <input type="file" name="`+cimageorder+`"  onchange="viewFile(this)";/>
              <input type="button" value="삭제" onClick="fn_remove(this)">
     </div>  
       
	`;		
	  document.querySelector('.file_list').appendChild(fileDiv);
	  cimageorder++;
  } 
 
  function fn_change(element){
	console.log(element.parentElement.querySelector('input[name="changeimg"]'));
	element.parentElement.querySelector('input[name="changeimg"]').value='true'
	;
  }  
  
  function fn_up(element){
	
	//const nodefilename=element.parentElement.parentElement.querySelector('input[type=file]').getAttribute("name");
	//console.log(nodefilename);
	//element.parentElement.parentElement.previousSibling.querySelector('input[type=file]').setAttribute( 'name', 'https://kkamikoon.tistory.com' )
	//console.log(element.parentElement.parentElement.previousSibling.querySelector('input[type=file]'));
	
	const changenode= element.parentElement.parentElement.previousSibling.cloneNode(true);
	const node= element.parentElement.parentElement.cloneNode(true);
	console.log(changenode);
	
	 element.parentElement.parentElement.parentElement.replaceChild(node, element.parentElement.parentElement.previousSibling);
	element.parentElement.parentElement.parentElement.replaceChild(changenode,element.parentElement.parentElement);
  }
  
  function fn_down(element){
	
	const changenode= element.parentElement.parentElement.nextSibling.cloneNode(true);
	const node= element.parentElement.parentElement.cloneNode(true);
	element.parentElement.parentElement.parentElement.replaceChild(node, element.parentElement.parentElement.nextSibling);
	element.parentElement.parentElement.parentElement.replaceChild(changenode,element.parentElement.parentElement);
	
  }
  