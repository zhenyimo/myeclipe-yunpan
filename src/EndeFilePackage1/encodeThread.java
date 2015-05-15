package EndeFilePackage1;

import java.util.Hashtable;

public class encodeThread implements Runnable {
	public static Hashtable<Integer,byte[]> encoded_list=new Hashtable<Integer,byte[]>();
	public static byte[] publicKey=null;
	private int number=0;
	private byte[] decoded_data=null;
	public encodeThread(int number,byte[] decoded_data,int start,int end){
		this.number=number;
		this.decoded_data=new String(decoded_data).substring(start,end).getBytes();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("number:"+number+"::encoding");
		byte[] encodedData=null;
		try {
			encodedData = ElGamalCoder.encryptByPublicKey(decoded_data, publicKey);
			 encoded_list.put(number, encodedData);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("number:"+number+"::encode_failure");
		}
		System.out.println("number:"+number+"::encode_success");
		
	}

}
