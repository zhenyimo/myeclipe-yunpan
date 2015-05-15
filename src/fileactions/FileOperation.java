package fileactions;

import hdfs.hdfsOperation;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


import org.apache.hadoop.fs.Path;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import fileactions.jsonFile;



public class FileOperation {
	//private static Pattern p=Pattern.compile("^F:/go(.+?)$");
	

	//罗列出文件夹文件
	public static boolean getJsonFile(String relaPath,List<jsonFile> list){
		//File f=FileOperation.getFile(path);
		Path hdfsPath=hdfsOperation.getHadoopPath(relaPath);
		return hdfsOperation.add2Array(hdfsPath,list);
	}
	
	//文件夹创建
	public static boolean FolderCreate(String root){
		String rp=getRelativePath(root,"NewFolder");
		return hdfsOperation.FolderCreate(rp);
	}
	
	//文件删除
	public static boolean FileDelete(String root,JSONArray files){
		String rp=null;
		JSONObject tmp=null;
		for(int i=0;i<files.size();i++){
			tmp=files.getJSONObject(i);
			rp=getRelativePath(root,tmp.getString("name"));
			if(!hdfsOperation.FileDelete(rp)){
				return false;
			}
		}
		return true;
	}
	
	
	//内容查询
	
	public static boolean FileContentSearch(String path,String queryTerm,ArrayList<jsonFile> fps){
		return hdfsOperation.FileContentSearch(path,queryTerm,fps);
	}
	//文件重命名
	public static boolean FileRename(String root,String oldName,String newName){
		String orp=getRelativePath(root,oldName);
		String nrp=getRelativePath(root,newName);
		return hdfsOperation.FileRename(orp,nrp);
	}
	
	//上传文件
	public static  boolean copyFile(File src,String relaDst,String webRoot){
		 	try {
		 		Path hdfsPath=hdfsOperation.getHadoopPath(relaDst);
		 		if(TransformUnit.docCheck(relaDst)){
		 			return hdfsOperation.uploadDocFile(src, hdfsPath, webRoot);
		 		}
		 		else return hdfsOperation.uploadFile(src, hdfsPath);
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		 	
	}
	
	
	//文件查找
	public static boolean searchFile(int typ,List<jsonFile> list){
		Path hdfsPath=hdfsOperation.getHadoopPath("/");
		return hdfsOperation.searchFile(typ,list,hdfsPath);
	} 
	
	//文件下载
	
	public static String getRelativePath(String root,String fileName){
		if(root.equals("/")){
			return root+fileName;
		}
		else{
			return root+"/"+fileName;
		}
}
	
	public static InputStream getDownloadStream0(String root,String fileName,String webRoot){
		try {
			String rp=getRelativePath(root, fileName) ;
			if(TransformUnit.docCheck(fileName)){
				return new FileInputStream(hdfsOperation.getDownloadTmpFile(rp,fileName,webRoot));
			}
			else return new FileInputStream(hdfsOperation.getDownloadNotDocFile(rp, fileName, webRoot));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static InputStream getDownloadStream1(String localpath){
		try {
			return new FileInputStream(localpath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
