package EndeFilePackage1;

import java.io.File;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class Integration {
	private String fileName;
	private String filterName;
	
	public Integration(String fileName,String filterName){
		this.fileName = fileName;
		this.filterName = filterName;
	}
	
	public void unite() throws Exception{
		String[] tt;
		File inFile = new File(".");//在当前目录下的文件
		File outFile = new File(fileName);//取得输出名
		RandomAccessFile out = new RandomAccessFile(outFile, "rw");
		
		//取得符合条件的文件名
		tt = inFile.list(new FilenameFilter() {			
			@Override
			 public boolean accept(File f, String name) {
		        StringTokenizer st = new StringTokenizer(name, ".");
		        String token = "";
		        while (st.hasMoreTokens()) {
		            token = st.nextToken();
		        }
		        if (token.equals(filterName)){
		            return true;
		        } else {
		            return false;
		        }
		    }
		});
		System.out.println("tt.length:"+tt.length);
		//打印出取得的文件名
		for(int i=0;i<tt.length;i++){
			System.out.println(tt[i]);
		}
		
		//打开所有的文件再写入到一个文件中
		for(int i=0;i<tt.length;i++){
			inFile = new File(tt[i]);
			RandomAccessFile in = new RandomAccessFile(inFile, "r");
			int c;
			while((c=in.read())!=-1){
				out.write(c);
			}
		}
		out.close();
	}
		
}