package EndeFilePackage1;

public class Change{
	
	public static String String(byte[] bytes) throws Exception{
	    String isoString = new String(bytes,"UTF-8");
		return isoString;
	}
	public static byte[] toByte(String str)throws Exception{
		byte[] bytes=str.getBytes("UTF8");
		return bytes;
	}

}
