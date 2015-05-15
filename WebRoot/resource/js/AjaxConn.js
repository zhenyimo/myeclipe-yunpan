//刷新文件表格

function refreshFileTable(res){
		var tb=bsFrame.window.document.getElementById("contain-table");
		var pNode=bsFrame.window.document.getElementById("path");
		//删除所有行
	    var rowNum=tb.rows.length;
	    var i=1;
	    for (;i<rowNum;i++)
	    {
	        tb.deleteRow(i);
	        rowNum=rowNum-1;
	        i=i-1;
	    }
	    var j=0;
	    var a1;
	    var a2;
	    var select1;
	    for(;j<res.length;j++){
	    	newRow=tb.insertRow();
			newRow.class="contain-list";
			newCell1=newRow.insertCell();
			newCell2=newRow.insertCell();
			newCell3=newRow.insertCell();
			newCell4=newRow.insertCell();
			newCell5=newRow.insertCell();
			newCell6=newRow.insertCell();
			
			
			newCell1.class="checked";
			newCell1.innerHTML="<input type='checkbox' />";
			
			newCell2.class="doc-name";
			newCell2.innerHTML=res[j].name;
			newCell2.setAttribute("style","cursor: pointer;");
			newCell2.onclick=function(){Open(this,pNode);};
			
			newCell3.innerHTML=res[j].size;
			newCell4.innerHTML=res[j].type;
			newCell5.innerHTML=res[j].date;
			
			a1 =document.createElement("a");
			a2=document.createElement("a");
			select1=document.createElement("select");
			
			a1.setAttribute("style", "font-size: 14px;margin-right: 20px;");
			a1.href="javascript:void(0)";
			a1.onclick=function(){deleteFileController(this);};
			a1.innerHTML="删除";
			
			a2.setAttribute("style", "font-size: 14px;margin-right: 20px;");
			a2.href="javascript:void(0)";
			a2.innerHTML="下载";
			a2.onclick=function(){
				downLoadController(this,pNode);			
			};
			

			select1.setAttribute("style","color: #6c6c6c;font-family: '微软雅黑';outline: none;");
			select1.options.add(new Option("更多","0"));
			select1.options.add(new Option("重命名","1"));
			select1.options.add(new Option("移动到","2"));
			select1.onchange=function(){selectChosenFactory(this,pNode);};
			
			newCell6.appendChild(a1);
			newCell6.appendChild(a2);
			newCell6.appendChild(select1);		
	    }
	    
		
}

//查询后的刷新
function refreshAfterSearch(res){
	var tb=bsFrame.window.document.getElementById("contain-table");
	var pNode=bsFrame.window.document.getElementById("path");
	tb.rows[0].cells[5].innerHTML="路径";
	//删除所有行
    var rowNum=tb.rows.length;
    var i=1;
    for (;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
    var j=0;

    for(;j<res.length;j++){
    	newRow=tb.insertRow();
		newRow.class="contain-list";
		newCell1=newRow.insertCell();
		newCell2=newRow.insertCell();
		newCell3=newRow.insertCell();
		newCell4=newRow.insertCell();
		newCell5=newRow.insertCell();
		newCell6=newRow.insertCell();
		
		
		newCell1.class="checked";
		newCell1.innerHTML="<input type='checkbox' />";
		
		newCell2.class="doc-name";
		newCell2.innerHTML=res[j].name;
		newCell2.setAttribute("style","cursor: pointer;");
		newCell2.onclick=function(){downLoadAfterSearch(this);};
		
		newCell3.innerHTML=res[j].size;
		newCell4.innerHTML=res[j].type;
		newCell5.innerHTML=res[j].date;
		
		newCell6.innerHTML=res[j].path;	
    }
    
	
}




function selectChosenFactory(chosen,pNode){
	var RenameStr="http://localhost:8080/yunpan/FileRename.action";
	if(chosen.value=="1"){
		//文件重命名
		Ext.MessageBox.prompt("文件重命名","请输入新名字:",function(btn,txt){
			if(btn=="ok"){
				var oldName=chosen.parentNode.parentNode.childNodes[1];
				var oldNameStr=oldName.innerHTML;
				var path=pNode.innerHTML;
				Ext.Ajax.request({  
			        url : RenameStr,  
			        params : {path: path,oldName:oldNameStr,newName:txt},  
			        method : 'POST',  
			        success : function(response) {  
			                Ext.Msg.show({
									title : "文件重命名",
									msg : "重命名成功",
									buttons : Ext.Msg.OK,
									animEl : Ext.getBody(),//small to big
									icon : Ext.MessageBox.QUESTION
								});
			                oldName.innerHTML=txt;
			        },  
			        failure : function() {  
			            Ext.Msg.show({
									title : "文件重命名",
									msg : "文件重命名失败!",
									buttons : Ext.Msg.OK,
									animEl : Ext.getBody(),// 
									icon : Ext.MessageBox.QUESTION
								}); 
			        }  
			    }); 
			}
		});
	}
}


function Open(x,pNode){
	var fileName=x.innerHTML;
	var type=x.nextSibling.nextSibling;
	if(type.innerHTML=="dir"){
		listFile(fileName,pNode);
	}
	else{
		downloadFileCall(fileName,pNode.innerHTML,0);
	}
	
}

function listFile(fileName,pNode){
	var rp;
	var p=pNode.innerHTML;
	if(p=="/"){
		rp=p+fileName;
		
	}
	else{
		rp=p+"/"+fileName;
	}
	listFileCall(rp,pNode);
	
}


function allList(){	
	var pNode=bsFrame.window.document.getElementById("path");
	listFileCall("/",pNode);
}



//新建文件夹
function newFolderCall(){
	var pNode=bsFrame.window.document.getElementById("path");
	var rp=pNode.innerHTML;
	var urlStr="http://localhost:8080/yunpan/FolderCreate.action";
	
	var req={  
            url : urlStr,  
            params : {path: rp},  
            method : 'POST',  
            success : function(response) { 
                    Ext.Msg.show({
							title : "文件夹创建",
							msg : "文件夹创建成功！",
							buttons : Ext.Msg.OK,
							animEl : Ext.getBody(),//small to big
							icon : Ext.MessageBox.QUESTION
						});
                    listFileCall(rp,pNode);
            },  
            failure : function() {  
                Ext.Msg.show({
							title : "文件夹创建",
							msg : "文件夹创建失败!",
							buttons : Ext.Msg.OK,
							animEl : Ext.getBody(),// 
							icon : Ext.MessageBox.QUESTION
						}); 
            }  
        };
   Ext.Ajax.request(req);
}

//文件列表
function listFileCall(rp,pNode) {  
//	var path=document.getElementById("path");
	var urlStr="http://localhost:8080/yunpan/FileList.action"
//	var paramsObj="{path:"+path+"}";
    Ext.Ajax.request({  
                url : urlStr,  
                params : {path: rp},  
                method : 'POST',  
                success : function(response) {  
                        var result = Ext.JSON.decode(response.responseText);
                        
                        pNode.innerHTML=rp;
                        
                       
/*                        Ext.Msg.show({
								title : "success",
								msg : result.length,
								buttons : Ext.Msg.OK,
								animEl : Ext.getBody(),//small to big
								icon : Ext.MessageBox.QUESTION
							});*/
                        refreshFileTable(result);
                },  
                failure : function() {  
                    Ext.Msg.show({
								title : "failure",
								msg : "Error!",
								buttons : Ext.Msg.OK,
								animEl : Ext.getBody(),// 
								icon : Ext.MessageBox.QUESTION
							}); 
                }  
            });  
	 
} 



//文件内容查询
function FileContentSearch(event){
	var event=arguments.callee.caller.arguments[0]||window.event;
	if(event.keyCode===13){
		var pNode=bsFrame.window.document.getElementById("path");
		var rp=pNode.innerHTML;
		var queryTerm=document.getElementById("search").value;
		var urlStr="http://localhost:8080/yunpan/FileContentSearch.action";
		 Ext.Ajax.request({  
	         url : urlStr,  
	         params : {path: rp,queryTerm:queryTerm},  
	         method : 'POST',  
	         success : function(response) {  
	                 var result = Ext.JSON.decode(response.responseText);
	                 Ext.Msg.show({
								title : "内容查询",
								msg :result,
								buttons : Ext.Msg.OK,
								animEl : Ext.getBody(),//small to big
								icon : Ext.MessageBox.QUESTION
							});
	                 document.getElementById("search").value="";
	                 refreshAfterSearch(result);
	         },  
	         failure : function() {  
	             Ext.Msg.show({
								title : "内容查询",
								msg : "查询失败!",
								buttons : Ext.Msg.OK,
								animEl : Ext.getBody(),// 
								icon : Ext.MessageBox.QUESTION
							}); 
	         }  
	     });  
	}
	
}


//文件查询

function FileSearch(x){
	var urlStr="http://localhost:8080/yunpan/FileExtSearch.action";
	Ext.Ajax.request({  
         url : urlStr,  
         params : {typ:x},  
         method : 'POST',  
         success : function(response) {  
                 var result = Ext.JSON.decode(response.responseText);
                 Ext.Msg.show({
							title : "文件查询",
							msg :result,
							buttons : Ext.Msg.OK,
							animEl : Ext.getBody(),//small to big
							icon : Ext.MessageBox.QUESTION
						});
                 refreshAfterSearch(result);
         },  
         failure : function() {  
             Ext.Msg.show({
							title : "文件查询",
							msg : "查询失败!",
							buttons : Ext.Msg.OK,
							animEl : Ext.getBody(),// 
							icon : Ext.MessageBox.QUESTION
						}); 
         }  
     }); 
}


//文件上传

function uploadFileCall(){
	var pNode=bsFrame.window.document.getElementById("path");
	var path=pNode.innerHTML;
	var progressBar;
	var form = new Ext.FormPanel({
            //labelAlign: 'top',
            bodyStyle: 'padding:5px 5px 0',//内容和panel的距离.up,right,down,left;
            layout: 'form',
            fileUpload:true,
			items:[
				{
					xtype:'filefield',
					fieldLabel:'文件选择',
					name:'file',
					allowBlank:false
				}
			],
            buttonAlign: 'center',
            buttons:[
            	{
				text:"上传",
				handler:function() {
					if(form.getForm().isValid()){
							progressBar=Ext.Msg.show({
							title:'上传',
							msg:'上传进度',
							progress:true,
							width:300,
							button:{cancel:'cancel'},
							closeable:false
						});
						form.getForm().submit(
							{
							url:"http://localhost:8080/yunpan/FileUpload.action?path="+path,
							type:'ajax',
							success:function(form,action){
									progressBar.hide();
									Ext.Msg.show({
										title : "文件上传",
										msg : "上传成功",
										buttons : Ext.Msg.OK,
										animEl : Ext.getBody(), 
										icon : Ext.MessageBox.QUESTION
									});
									listFileCall(path,pNode);		 
							},
							failure : function() {  
                   					 Ext.Msg.show({
										title : "failure",
										msg : "Error!",
										buttons : Ext.Msg.OK,
										animEl : Ext.getBody(),// 
										icon : Ext.MessageBox.QUESTION
									}); 
               			    } 		
						});
						var task={
							run:function(){
										var urlStr="http://localhost:8080/yunpan/FileProgressBar.action";
										var f_times=0;
										Ext.Ajax.request(
										{
											url:urlStr+"?t="+new Date(),
											method:"GET",
											success:function(response,options){
														var result = Ext.JSON.decode(response.responseText);
														var t=result.rate/100.00;
														if(t>=1){
												   			 progressBar.updateProgress(0.99,"99%", "正在上传");
												   			 Ext.TaskManager.stop(task);
														}
														else{	
															progressBar.updateProgress(t,result.rate+"%","正在上传");
														}									
									    	},
											failure:function(){
												f_times+=1;
												if(f_times>3){
													progressBar.hide();
													Ext.TaskManager.stop(task);
													Ext.Msg.show({
														title : "failure",
														msg : "上传失败!",
														buttons : Ext.Msg.OK,
														animEl : Ext.getBody(),
														icon : Ext.MessageBox.QUESTION
													});
												}
												
											}										
										}
										);							
							},
							interval:1000
						}
						Ext.TaskManager.start(task);			
					}
					else{
						 Ext.Msg.show({
								title : "failure",
								msg : "表单出错!",
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.QUESTION,
								animEl : Ext.getBody()// ?????????Ч??
							}); 
					}
				}
			},
			{
				text:'关闭',
				handler:function(){
					win.close(this);
				}
			}
            ]
        });
        var win = Ext.create("Ext.window.Window", {
            title: "文件上传",       //标题
            draggable: false,
            height: 200,                          //高度
            width: 500,                           //宽度
            layout: "fit",                        //窗口布局类型
            modal: true, //是否模态窗口，默认为false
            resizable: false,
            items: [form]
        });
        win.show();
}

 //文件下载
function downloadFileCall(fileName,p,type){
	var urlStr="http://localhost:8080/yunpan/FileDownload.action?path="+p+"&fileName="+fileName+"&type="+type;
	window.open(urlStr);
}
function downLoadController(x,pNode){
	var fileName=x.parentNode.parentNode.childNodes[1].innerHTML;
	downloadFileCall(fileName,pNode.innerHTML);
}

function downLoadAfterSearch(x){
	var rp=x.parentNode.cells[5].innerHTML;
	var fileName=x.parentNode.cells[1].innerHTML;
	downloadFileCall(fileName,rp,1);
}


//删除文件

function deleteFileController(x){
	var checkedRow=x.parentNode.parentNode;
	var check=checkedRow.getElementsByTagName("input");
	check[0].checked=true;
	del();
}
function deleteFileCall(rp,files){
//	var path=document.getElementById("path");
	var urlStr="http://localhost:8080/yunpan/FileDelete.action";
//	var paramsObj="{path:"+path+"}";
	var req={  
            url : urlStr,  
            params : {path: rp,fileName:files},  
            method : 'POST',  
            success : function(response) { 
                    Ext.Msg.show({
							title : "文件删除",
							msg : "成功删除！",
							buttons : Ext.Msg.OK,
							animEl : Ext.getBody(),//small to big
							icon : Ext.MessageBox.QUESTION
						});
                    delcheck();
            },  
            failure : function() {  
                Ext.Msg.show({
							title : "文件删除",
							msg : "文件删除失败!",
							buttons : Ext.Msg.OK,
							animEl : Ext.getBody(),// 
							icon : Ext.MessageBox.QUESTION
						}); 
            }  
        };
   Ext.Ajax.request(req);  
}




