package EndeFilePackage1;

import java.io.File;
import java.io.RandomAccessFile;

public class Split {
	private String fileName;
	private int size;
	
	public Split(String fileName,String size){
		this.fileName = fileName;
		this.size = Integer.parseInt(size)*1024/8;
	}
	
	public void cut() throws Exception{
		int maxx = 0;
		File inFile = new File(fileName);
		
		int fileLength = ((int)inFile.length());//取得文件大小
		int value;//取得要分割的个数
		
		//打开要分割的文件
		RandomAccessFile in = new RandomAccessFile(inFile, "r");
		
		value = fileLength/size;
		int i=0;
		int j=0;
		//根据要分割的数目输出文件
		for(;j<value;j++){
			File outFile = new File("心得"+j+".txt");
			RandomAccessFile out = new RandomAccessFile(outFile, "rw");
			maxx+=size;
			for(;i<maxx;i++){
				out.write(in.read());				
			}			
			out.close();
		}
		System.out.println("i:"+i);
		File outFile = new File("心得"+j+".txt");
		RandomAccessFile out = new RandomAccessFile(outFile, "rw");
		for(;i<fileLength;i++){
			out.write(in.read());
		}		
		out.close();
		in.close();
	}
	
}