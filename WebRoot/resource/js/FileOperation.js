



//ɾ���ܵ�ʵ��
function judge(x){
	//ȫѡ
	var ax = document.getElementById("contain-table");
	var check = ax.getElementsByTagName("input");
	if(x.checked){
		for(var i = 0; i < check.length; i++)
			check[i].checked = true;
	}
	else{
		for(var i = 0; i < check.length; i++)
			check[i].checked = false;
	}	
}


function del(){
	
	var doc;
	if(window.top==window.self){
		doc=bsFrame.window.document;
	}
	else doc=document;
	var ax=doc.getElementById("contain-table");
	var path=doc.getElementById("path").innerHTML;
	var check=ax.getElementsByTagName("input");
	var files=[];
	var fileName={};
	
	for(var i=0;i<check.length;i++){
		if(check[i].checked==true){
			fileName.name=check[i].parentNode.nextSibling.innerHTML;
			files.push(fileName);
			fileName={};
		}
	}
	alert(files);
	deleteFileCall(path,JSON.stringify(files));
}

function delcheck(){
	//ɾ��ѡ�е�
	var doc;
	if(window.top==window.self){
		doc=bsFrame.window.document;
	}
	else doc=document;
	var x = doc.getElementById("contain-table").getElementsByTagName("input");
	for (var i = x.length - 1; i >= 0; i--) {
		if(x[i].checked){
			x[i].parentNode.parentNode.remove();
		}
	}
}






