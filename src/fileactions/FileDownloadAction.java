package fileactions;

import com.opensymphony.xwork2.Action;

import hdfs.hdfsOperation;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;



import org.apache.struts2.ServletActionContext;


public class FileDownloadAction implements Action {
	private String path;
	private String fileName;
	private int type;
	
	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	//如果下载文件名为中文，进行字符编码转换
    public String getDownloadChineseFileName() {
        String downloadChineseFileName = fileName;
 
        try {
            downloadChineseFileName = new String(downloadChineseFileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        return downloadChineseFileName;
    }

	public InputStream getDownloadFile() {
		String webRoot=ServletActionContext.getServletContext().getRealPath("/");
		InputStream is=null;
		if(type==0){
			is=FileOperation.getDownloadStream0(this.path,this.fileName,webRoot);
		}
		else if(type==1){
			is=FileOperation.getDownloadStream1(hdfsOperation.getDownloadTmpFile(this.path, fileName, webRoot));
		}		
		if(is!=null){
			return is;
		}
			
		else {
			return null;
		}
	}




	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}

}

