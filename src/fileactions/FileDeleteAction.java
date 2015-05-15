package fileactions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.Action;

public class FileDeleteAction implements Action {
	private String path;
	private String fileName;
	private boolean success=false;
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if((path==null)||(fileName==null)){
			this.success=false;
		}
		else{
			JSONArray files=JSON.parseArray(fileName);
			if(FileOperation.FileDelete(path, files)){
				this.success=true;
			}
			else this.success=false;
		}
		return SUCCESS;
	}

}
