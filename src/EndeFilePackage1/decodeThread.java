package EndeFilePackage1;

import java.util.Hashtable;

public class decodeThread implements Runnable {
	public static Hashtable<Integer,byte[]> decoded_list=new Hashtable<Integer,byte[]>();
	public static byte[] privateKey=null;
	private int number=0;
	private byte[] encodedData=null;
	public decodeThread(int number,byte[] encoded_data){
		this.number=number;
		this.encodedData=encoded_data.clone();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("number:"+number+"::decoding");
		try {
			byte[] decodedData = ElGamalCoder.decryptByPrivateKey(encodedData, privateKey);
			decoded_list.put(number, decodedData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("number:"+number+"::decode_failure");
		}
		System.out.println("number:"+number+"::decode_success");
	}
}
