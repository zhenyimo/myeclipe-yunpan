package fileactions;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;

public class FileExtSearchAction implements Action {
	private Object resultList;
	private boolean success=true;
	private Integer typ;
	
	
	public Object getResultList() {
		return resultList;
	}


	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}


	public boolean isSuccess() {
		return success;
	}

	



	public Integer getTyp() {
		return typ;
	}


	public void setTyp(Integer typ) {
		this.typ = typ;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("type::"+typ);
		int typ1=this.typ.intValue();
		if(typ1>3||typ1<0){
			this.success=false;
		}
		else {
			List<jsonFile> list=new ArrayList<jsonFile>();
			if(FileOperation.searchFile(typ1,list)){
				this.success=true;
				this.resultList=JSON.toJSON(list);
				System.out.println(this.resultList);
			}
			else {
				this.success=false;
				this.resultList=null;
			}
		}
		return Action.SUCCESS;
	}

}
