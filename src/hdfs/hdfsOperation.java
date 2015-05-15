package hdfs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import luceneSearch.LuceneOperation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import EndeFilePackage1.EnDeFile;



import fileactions.jsonFile;

public class hdfsOperation {
	private static String basePath="hdfs://localhost:9000/mo";
	private static String[][] exts={{".*doc",".*txt",".*pdf",".*html"},{".*jpeg",".*jpg",".*gif",".*png",".*bmp"},{".*mp3",".*mp4",".*wmv",".*wav"},{".*avi",".*rmvb",".*mkv"}};
	public static FileSystem getFs() {
			Configuration conf = new Configuration();
			try {
				return FileSystem.get(conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	public static String transFormRelativPath(String hdfsPath){
		return hdfsPath.replaceAll("hdfs://localhost:9000/mo/work","").trim();
	}
	
	
	public static Path getIndexPath(){
		return new Path("hdfs://localhost:9000/mo/index");
	}
	

	public static Path getHadoopPath(String relaPath){
			System.out.println("文件路径："+basePath+"/work"+relaPath);
			return new Path(basePath+"/work"+relaPath);
	}
	
	
	public static String getFileExtend(String fileName){
		int n=fileName.lastIndexOf('.');
		if(n==-1){
			return null;
		}
		else return fileName.substring(n+1);
	}
	//文件夹创建
	public static boolean FolderCreate(String rp){
		FileSystem fs=hdfsOperation.getFs();
		Path hdfsPath=getHadoopPath(rp);
		try {
			if(fs.exists(hdfsPath)){
				System.out.println("文件夹创建::文件夹已存在::"+hdfsPath.toString());
				Date date=new Date();
				hdfsPath=getHadoopPath(rp+date.getTime());
			}
			fs.mkdirs(hdfsPath);
			System.out.println("文件夹创建成功::"+hdfsPath.toString());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//文件重命名
	public static boolean FileRename(String orp,String nrp){
		FileSystem fs=hdfsOperation.getFs();
/*		String orpt=orp;
		String nrpt=nrp;*/
		String tmp=orp;
		Path hdfsOPath=getHadoopPath(orp);
		Path hdfsNPath=getHadoopPath(nrp);
		System.out.println("替换::"+tmp.replaceAll("/.*(\\.).*/",""));
		try {
			if(fs.exists(hdfsOPath)&&(!fs.exists(hdfsNPath))){
				fs.rename(hdfsOPath, hdfsNPath);
				System.out.println("文件重命名成功::"+hdfsOPath.toString()+"::"+hdfsNPath.toString());
				
				return true;
			}
			else{
				System.out.println("文件重命名::文件不存在或文件已存在同一个名字::"+hdfsOPath.toString()+"::"+hdfsNPath.toString());
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
	}
	
	
	//文件删除
	public static boolean FileDelete(String rp){
		FileSystem fs=getFs();
		int r=0;
		String ext;
		Path hdfsPath=getHadoopPath(rp);
		try {
			if(!fs.exists(hdfsPath)){
				System.out.println("文件删除::文件不存在::"+hdfsPath.toString());
				return false;
			}
			else{		
				if(fs.isFile(hdfsPath)){
					r=rp.indexOf('.');
					if(r!=-1){
						ext=rp.substring(r+1);
						if(ext.equals("txt")||ext.equals("pdf")||ext.equals("doc")){
							LuceneOperation.indexDel(hdfsPath.toString(), getIndexPath());
						}
					}
				}
				fs.delete(hdfsPath);
				System.out.println("文件删除::文件删除::"+hdfsPath.toString());
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	//文件罗列
	public static boolean add2Array(Path hdfsPath,List<jsonFile> list){
			FileSystem fs=hdfsOperation.getFs();
			try {
				if(!fs.exists(hdfsPath)){
					System.out.println("您所输入的文件不存在！");
					return false;
				}
				FileStatus[] files=fs.listStatus(hdfsPath);
				for(FileStatus file :  files){
					 list.add(new jsonFile(file));
				}
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	
	//内容查询
	public static boolean FileContentSearch(String path,String queryTerm,ArrayList<jsonFile> fps){
		Path hdfsPath=getHadoopPath(path);
		return LuceneOperation.queryByContent(hdfsPath,queryTerm,hdfsOperation.getIndexPath(), fps);
	}
	
	
	
	
	//文件上传
	
	
	public static boolean uploadFile(File src,Path hdfsPath){
		FileSystem fs=hdfsOperation.getFs();
		try {
			if(fs.exists(hdfsPath)){
				System.out.println("文件已存在::"+hdfsPath);
				 return false;
			}
			BufferedInputStream bis=new BufferedInputStream(new FileInputStream(src));
			Configuration conf=new Configuration();
			
			Progressable progress = new Progressable(){
				public void progress() {					
					System.out.print(".");
				}			
			};
			FSDataOutputStream out = fs.create(hdfsPath,progress);
			IOUtils.copyBytes(bis, out, conf);
			System.out.println("文件上传::"+hdfsPath+"::成功");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean uploadDocFile(File src,Path hdfsPath,String webRoot){
	 		File f=new File(webRoot+"\\Key");
	 		String tp=null;
	 		EnDeFile edFile=new EnDeFile(f.getPath());
			if(!f.mkdir()){
				System.out.println("临时文件夹已存在::"+webRoot+"\\Key");
			}		
			else {
				System.out.println("临时文件创建成功::"+webRoot+"\\Key");
			}
			try {
				edFile.initKey();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			
			File tdir=new File(webRoot+"\\Key\\tmpEnDeDir");
			if(!tdir.mkdir()){
				System.out.println("临时文件夹创已存在::"+tdir.getPath());
			}
			else{
				System.out.println("临时文件夹创建成功::"+tdir.getPath());
			}
			
			try {
				tp=tdir.getPath()+"\\"+hdfsPath.getName().toString();
				System.out.println("临时文件路径::"+tp);
				edFile.fileEncode(src,tp);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("文件加密出错");
			}
			
			
			try {
				BufferedInputStream bis=new BufferedInputStream(new FileInputStream(tp));
				FileSystem fs=hdfsOperation.getFs();
				if(fs.exists(hdfsPath)){
					System.out.println("文件已存在::"+hdfsPath);
					 return false;
				}
				Progressable progress = new Progressable(){
					public void progress() {					
						System.out.print(".");
					}			
				};
				
				String tmpName=hdfsPath.getName();
				String ext=getFileExtend(tmpName);
				if(ext==null){
					System.out.println("扩张名缺少");
					return false;
				}
				FSDataOutputStream out = fs.create(hdfsPath,progress);
				Configuration conf=new Configuration();
				IOUtils.copyBytes(bis, out, conf);
				File tf=new File(tp);
				tf.delete();
				System.out.println("删除临时文件::"+tp);
				System.out.println("文件上传成功::"+hdfsPath);
				System.out.println("文件正在添加索引");
				Path indexPath=getIndexPath();
				return LuceneOperation.indexAdd(src, hdfsPath, hdfsPath.getName(), ext, indexPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}
	
//文件下载
		public static String getDownloadNotDocFile(String rp,String fileName,String webRoot){
			Path hdfsPath=hdfsOperation.getHadoopPath(rp);
			File dst=null;
			FSDataInputStream fdis=null;
			FileSystem fs=hdfsOperation.getFs();
			try {
				if(!fs.exists(hdfsPath)){
					System.out.println("文件下载::hdfs文件不存在::"+hdfsPath);
					 return null;
				}
				fdis=fs.open(hdfsPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			System.out.println("文件下载::本地临时文件路径::"+webRoot+"\\webTmp");
			File f=new File(webRoot+"\\webTmp");
			if(!f.mkdir()){
				System.out.println("临时文件夹已存在::"+webRoot+"\\webTmp");
			}		
			else System.out.println("临时文件创建成功::"+webRoot+"\\webTmp");
			Date date=new Date();
			File tmpfile=new File(f.getPath()+"\\"+date.getTime()+fileName);
				Configuration conf=new Configuration();
				OutputStream bfout;
				try {
					bfout = new FileOutputStream(tmpfile);
					IOUtils.copyBytes(fdis, bfout, conf);
					bfout.flush();
					bfout.close();
					System.out.println("hdfs文件下载到本地成功::"+tmpfile.getPath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try {
						fdis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}
				return tmpfile.getPath();


			
		}
	
	
		public static String getDownloadTmpFile(String rp,String fileName,String webRoot) {
			// TODO Auto-generated method stub
			Path hdfsPath=hdfsOperation.getHadoopPath(rp);
			File dst=null;
			FSDataInputStream fdis=null;
			FileSystem fs=hdfsOperation.getFs();
			try {
				if(!fs.exists(hdfsPath)){
					System.out.println("文件下载::hdfs文件不存在::"+hdfsPath);
					 return null;
				}
				fdis=fs.open(hdfsPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			System.out.println("文件下载::本地临时文件路径::"+webRoot+"\\webTmp");
			File f=new File(webRoot+"\\webTmp");
			if(!f.mkdir()){
				System.out.println("临时文件夹已存在::"+webRoot+"\\webTmp");
			}		
			else System.out.println("临时文件创建成功::"+webRoot+"\\webTmp");
			Date date=new Date();
			File tmpfile=new File(f.getPath()+"\\"+date.getTime()+fileName);
			try {
				Configuration conf=new Configuration();
				OutputStream bfout=new FileOutputStream(tmpfile);
				IOUtils.copyBytes(fdis, bfout, conf);
				bfout.flush();
				bfout.close();
				System.out.println("hdfs文件下载到本地成功::"+tmpfile.getPath());
				String fk=webRoot+"\\Key";
				EnDeFile edf=new EnDeFile(fk);
				dst=new File(webRoot+"\\Key\\tmpEnDeDir\\"+tmpfile.getName());
				edf.initKey();
				edf.fileDecode(tmpfile, dst);
				System.out.println(tmpfile.delete());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			catch (IOException e){
				e.printStackTrace();
				tmpfile.delete();
				return null;
			}
			catch(Exception e){
				e.printStackTrace();
				tmpfile.delete();
				return null;
			}
			finally{
				try {
					fdis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			return dst.getPath();
		}

//文件查找		
		
		public static boolean searchFile(int typ, List<jsonFile> list, Path hdfsPath) {
			// TODO Auto-generated method stub
			FileSystem fs=hdfsOperation.getFs();
			
			 try {
				System.out.println("查询路径:"+hdfsPath.toString()+"/*");
				FileStatus[] status = fs.globStatus(new Path(hdfsPath.toString()+"/*"),new extFileFilter(exts[typ]));
				if(status==null||status.length==0){
					return true;
				}
				for(FileStatus fls:status){
					jsonFile jf=new jsonFile(fls);
					list.add(jf);
				}
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
}
