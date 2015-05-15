package fileactions;

import hdfs.hdfsOperation;

import org.apache.hadoop.fs.FileStatus;

public class jsonFile{
	String name;
	String type;
	String size;
	String date;
	String path;
	int k=0;
	
	public jsonFile(String name, String type,String size,String date,String path) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.type=type;
		this.size=size;
		this.path=hdfsOperation.transFormRelativPath(path);
	}
	
	public jsonFile(FileStatus file){
		this.name=file.getPath().getName();
		if(file.isDir()){
			this.type="dir";
		}
		else{
			String tmp=file.getPath().toString();
			k=tmp.lastIndexOf('.');
			if(k==-1){
				System.out.println("file:"+file.getPath());
				this.type=null;
			}
			else{
				tmp=tmp.substring(k+1);
				this.type=tmp;
			}
			
		}
		this.size=TransformUnit.FormatFileSize(file.getLen());
		this.date=TransformUnit.FormatFileDate(file.getModificationTime());
		this.path=hdfsOperation.transFormRelativPath(file.getPath().toString());
		//Log.info("文件名:"+this.name+"::路径:"+this.path);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}