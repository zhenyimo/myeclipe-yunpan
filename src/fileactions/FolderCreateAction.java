package fileactions;

import com.opensymphony.xwork2.Action;

public class FolderCreateAction implements Action {
	private String path;
	private boolean success=true;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(this.path==null){
			this.success=false;
		}
		else{
			if(FileOperation.FolderCreate(path)){
				this.success=true;
			}
			else this.success=false;
		}
		return SUCCESS;
	}

}
