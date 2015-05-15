package fileactions;
import com.opensymphony.xwork2.Action;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import luceneSearch.LuceneOperation;

import org.apache.struts2.ServletActionContext;


public class FileUploadAction implements Action {
	private File file;
	private String fileContentType;
	private String fileFileName;
	private String path;
	private boolean success=true;
	
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String rp;
		HttpSession session=ServletActionContext.getRequest().getSession();
		HttpServletRequest request=ServletActionContext.getRequest();
		String webRoot=ServletActionContext.getServletContext().getRealPath("/");
		this.path=request.getParameter("path");
		rp=FileOperation.getRelativePath(this.path, this.fileFileName);
		if(FileOperation.copyFile(file,rp,webRoot)){
				success=true;
				session.removeAttribute("state");
		}
		else{
			success=false;
			
		}
		return SUCCESS;
	}

}

