package fileactions;

import com.opensymphony.xwork2.Action;

public class FileRenameAction implements Action {
	private String path;
	private String oldName;
	private String newName;
	private boolean success;
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(path==null||oldName==null||newName==null){
			this.success=false;
		}
		else{
			if(FileOperation.FileRename(path, oldName, newName)){
				this.success=true;
			}
			else this.success=false;
		}
		return SUCCESS;
	}

}
