package fileactions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;

public class FileListAction implements Action {
	private String path;
	private boolean success=true;
	private Object resultList;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResultList() {
		return resultList;
	}

	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		this.path=request.getParameter("path");
		List<jsonFile> list=new ArrayList<jsonFile>();
		if(FileOperation.getJsonFile(path, list)){
			this.success=true;
			this.resultList=JSON.toJSON(list);
			System.out.println(this.resultList);
		}
		else {
			this.success=false;
			this.resultList=null;
		}
		return SUCCESS;
	}

}
