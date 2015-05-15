package fileactions;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;

public class FileContentSearchAction implements Action {
	private String path;
	private String queryTerm;
	private Object resultList;
	private boolean success=false;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQueryTerm() {
		return queryTerm;
	}

	public void setQueryTerm(String queryTerm) {
		this.queryTerm = queryTerm;
	}

	public Object getResultList() {
		return resultList;
	}

	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String query=new String(queryTerm.getBytes(),"UTF-8");
		System.out.println("查询::"+query);
		ArrayList<jsonFile> fps=new ArrayList<jsonFile>();
		fps.clear();
		if(FileOperation.FileContentSearch(path,query,fps)){
			this.success=true;
			this.resultList=JSON.toJSON(fps);
			System.out.println(this.resultList);
		}
		else {
			this.success=false;
			this.resultList=null;
		}
		return SUCCESS;
	}

}
