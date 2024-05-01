document.querySelector('#add').addEventListener('click',function(){
	let memNo =document.querySelector('#memberNo').value;
	let memName =document.querySelector('#memberName').value;
	let memPnt =document.querySelector('#memberPoint').value;
	const mem={memNo,memName,memPnt};
	tr=makeRow(mem);
		//	
    tr.addEventListener('click',function(e){
	e.stopPropagation();	
	document.querySelector('#memberNo').value=this.children[0].innerHTML;
	document.querySelector('#memberName').value=this.children[1].innerHTML;
	document.querySelector('#memberPoint').value=this.children[2].innerHTML;
    })
		//	
			document.querySelector('#tlist tbody').appendChild(tr);	
	
}
);

document.querySelector('#modify').addEventListener('click',function(){
	
	let no= document.querySelector('#memberNo').value;
	
	console.log(no);
	let check =document.querySelectorAll('#tlist tbody tr');
	
	console.log(check);
	check.forEach(function(item){
		console.log(item.children[0]);
		if(item.children[0].innerHTML==no){
			
			item.children[1].innerHTML=document.querySelector('#memberName').value;
		
			item.children[2].innerHTML=document.querySelector('#memberPoint').value;
		}
	})
})

document.querySelector('thead input[type="checkbox"]').addEventListener('change',function(){
	console.log('전체삭제');
	let check =document.querySelectorAll('tbody  input[type="checkbox"]');
	check.forEach(function(checkbox){
		console.log(checkbox);
		
		if(check.checked==document.querySelector('thead input[type="checkbox"]').checked){
		}
		else{
			checkbox.checked=document.querySelector('thead input[type="checkbox"]').checked;
		}
										
	}
	)
	}
	);
	
		
function makeRow(mem={memNo,memName,memPnt}){
	let tr= document.createElement("tr");
	
	for(let prop in mem){
		let td =document.createElement('td');
		td.innerText=mem[prop];
		tr.appendChild(td);
	}
	//
	let btn = document.createElement('button');
	btn.innerText='삭제';
	tr.appendChild(btn);
	btn.addEventListener('click',function(){
		console.log(this);
		this.parentElement.remove();
	})
	//
	
	let td=document.createElement('td');
	let chk=document.createElement('input');
	chk.setAttribute('type','checkbox');
	chk.addEventListener('change',changeRow());
	td.appendChild(chk);
	tr.appendChild(td);
    return tr;	      
     	
}
	
function changeRow(e){
	
}

//this.parentElement.remove 교체
//function removeRow(e){
	//e.stopPropagation();//상하위 요소로 이벤트 전파차단
	
//}
		

