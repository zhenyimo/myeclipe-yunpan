package EndeFilePackage1;
import org.apache.commons.codec.binary.Base64;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.*;
/**
 * ElGamal У��
 * @author Administrator
 * @version 1.0
 */
public class EnDeFile {
	//公钥
	private byte[] publicKey;
	//私钥
	private byte[] privateKey;
	private String Keydir;
	
		/**
	 * 初始化密钥
	 * @throws exception
	 */
	public void initKey()throws Exception{
		File kf=new File(Keydir+"\\mo.txt");
		if(!kf.exists()){
			Map<String,Object> keyMap = ElGamalCoder.initKey();
			publicKey = ElGamalCoder.getPublicKey(keyMap);
			privateKey = ElGamalCoder.getPrivateKey(keyMap);
			encodeThread.publicKey=publicKey;
			decodeThread.privateKey=privateKey;
			
			System.out.println(kf.getPath());
			FileWriter fw=new FileWriter(kf);
			
			byte[] publicKey2 = Base64.encodeBase64(publicKey);
			fw.write(Change.String(publicKey2)+"\n");
			System.out.println("公钥:\n" + Change.String(publicKey2));
			System.out.println("公钥长度:\n"+publicKey2.length);
			byte[] privateKey2 = Base64.encodeBase64(privateKey);
			fw.write(Change.String(privateKey2)+"\n");
			System.out.println("私钥:\n" + Change.String(privateKey2));
			System.out.println("私钥长度:\n"+privateKey2.length);
			fw.close();
		}
		else{
			FileReader fr=new FileReader(kf);
			BufferedReader br=new BufferedReader(fr);
			byte[] publicKey2=Change.toByte(br.readLine());
			publicKey=Base64.decodeBase64(publicKey2);
			byte[] privateKey2=Change.toByte(br.readLine());
			privateKey=Base64.decodeBase64(privateKey2);
			encodeThread.publicKey=publicKey;
			decodeThread.privateKey=privateKey;
			System.out.println("公钥:\n" + Change.String(publicKey2));
			System.out.println("公钥长度:\n"+publicKey2.length);
			System.out.println("私钥:\n" + Change.String(privateKey2));
			System.out.println("私钥长度:\n"+privateKey2.length);
			br.close();
			fr.close();
		}
		
	}
	
	public EnDeFile(String Keydir){
		this.Keydir=Keydir;
	}
	
	
	
	public byte[] getPublicKey() {
			return publicKey;
		}



		public void setPublicKey(byte[] publicKey) {
			this.publicKey = publicKey;
		}



		public byte[] getPrivateKey() {
			return privateKey;
		}



		public void setPrivateKey(byte[] privateKey) {
			this.privateKey = privateKey;
		}



	/**
	 * 校验 
	 * @throws Exception 
	 */
	
	public void test() throws Exception { 
		int pos=0;
		int end=0;
		int i=0;
		String inputStr ="HGGoWVj";
		end=inputStr.length();
		int n_64=end/64;
		byte[] data=new byte[64];
		ExecutorService en_pool = Executors.newFixedThreadPool(5);
		int n=0;
		for(;i<n_64;){                                      
			n=((ThreadPoolExecutor)en_pool).getActiveCount();
			System.out.println("i:"+i+",pos="+pos+",n="+n);
			if(n<5){
				inputStr.getBytes(pos,pos+64, data,0);
				en_pool.execute(new encodeThread(i,data,0,64));
				pos=pos+64;
				i++;
			}
			else{
				Thread.sleep(100);
				continue;
			}
		}
		
		if(pos<end){
			System.out.println("i:"+i+",pos="+pos);
			inputStr.getBytes(pos,end, data,0);
			
			en_pool.execute(new encodeThread(i,data,0,end-pos));
		}
		en_pool.shutdown();
		n=((ThreadPoolExecutor)en_pool).getActiveCount();
		while(n!=0){
			n=((ThreadPoolExecutor)en_pool).getActiveCount();
		}
		
		
		System.out.println("原文:"+inputStr);
		System.out.println("原文长度:"+end);
		int tn=encodeThread.encoded_list.size();
		System.out.println("size::"+tn);
		for(int j=0;j<tn;j++){
			System.out.println("密文"+j+":"+encodeThread.encoded_list.get(j).length);
		}
	/*	System.out.println("原文:\n" + inputStr);
		System.out.println("原文长度:\n"+data.length);
		byte[] encodedData = ElGamalCoder.encryptByPublicKey(data, publicKey);
		byte[] encodedData2 = Base64.encodeBase64(encodedData);
		System.out.println("加密后:\n" + Change.String(encodedData));
		System.out.println("加密后长度:\n"+encodedData.length);
		byte[] decodedData = ElGamalCoder.decryptByPrivateKey(encodedData, privateKey);
		String outputStr = new String(decodedData);
		System.out.println("解密后:\n" + outputStr);
		assertEquals(inputStr,outputStr);*/

		
		
	}
	
	
	public boolean fileEncode(File srcPath,String dstPath) throws Exception{
	    FileInputStream fileForInput = new FileInputStream(srcPath);//"F:\\ppt\\ppt.jpg"
	    byte[] bytes = new byte[64];
		int end=0;
		int i=0;
		int pos=0;
		end=fileForInput.available();
		int n_64=end/64;
		ExecutorService en_pool = Executors.newFixedThreadPool(5);
		int n=0;
		for(;i<n_64;){                                      
			n=((ThreadPoolExecutor)en_pool).getActiveCount();
			if(n<5){
				fileForInput.read(bytes);
				en_pool.execute(new encodeThread(i,bytes,0,64));
				i++;
				pos+=64;
			}
			else{
				Thread.sleep(100);
				continue;
			}
		}
		
		if(pos<end){
			fileForInput.read(bytes, 0, end-pos);
			en_pool.execute(new encodeThread(i,bytes,0,end-pos));
		}
		en_pool.shutdown();
		try {  
	           boolean loop = true;  
	           do {    //等待所有任务完成  
	               loop = !en_pool.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
	           } while(loop);  
        } 
		catch (InterruptedException e) {  
         e.printStackTrace();  
         return false;
        }  
		
	    fileForInput.close();
	    
		System.out.println("原文长度:"+end);
		int tn=encodeThread.encoded_list.size();
		System.out.println("size::"+tn);
		FileOutputStream fos=new FileOutputStream(dstPath);
		for(int j=0;j<tn;j++){
			System.out.println("密文"+j+":"+encodeThread.encoded_list.get(j).length);
			fos.write(encodeThread.encoded_list.get(j));			
		}
		encodeThread.encoded_list.clear();
		fos.flush();
		fos.close();
		return true;
	}
	
	public boolean fileDecode(File srcPath,File dstPath)throws Exception{
		  FileInputStream fileForInput = new FileInputStream(srcPath);//"F:\\ppt\\ppt.jpg"
		    byte[] bytes = new byte[128];
			int end=0;
			int i=0;
			end=fileForInput.available();
			int n_128=end/128;
			if(end%128!=0){
				System.out.println("错误加密文件");
				return false;
			}
			ExecutorService de_pool = Executors.newFixedThreadPool(5);
			int n=0;
			for(;i<n_128;){                                      
				n=((ThreadPoolExecutor)de_pool).getActiveCount();
				if(n<5){
					fileForInput.read(bytes);
					de_pool.execute(new decodeThread(i,bytes));
					i++;
				}
				else{
					Thread.sleep(100);
					continue;
				}
			}			
			de_pool.shutdown();
			try {  
		           boolean loop = true;  
		           do {    //等待所有任务完成  
		               loop = !de_pool.awaitTermination(2, TimeUnit.SECONDS);  //阻塞，直到线程池里所有任务结束
		           } while(loop);  
	        } 
			catch (InterruptedException e) {  
	            e.printStackTrace();
	            return false;
	        }  
		    fileForInput.close();  
			System.out.println("原文长度:"+end);
			int tn=decodeThread.decoded_list.size();
			FileOutputStream fos=new FileOutputStream(dstPath);
			for(int j=0;j<tn;j++){
				System.out.println("明文"+j+":"+decodeThread.decoded_list.get(j).length);
				fos.write(decodeThread.decoded_list.get(j));			
			}
			decodeThread.decoded_list.clear();
			fos.flush();
			fos.close();	
			return true;
	}
	
	public static void main(String[] args){
		
	}
		
}