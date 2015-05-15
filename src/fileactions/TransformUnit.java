package fileactions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransformUnit {
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Date date = new Date();
	private static String[] exts={"doc","pdf","html","txt"};
	
	public static String FormatFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	//文件日期转换
	public static String FormatFileDate(long lasttime){
		date.setTime(lasttime);
		return df.format(date);
	}
	
	public static boolean docCheck(String ext){
		for(String s:exts){
			if(ext.lastIndexOf(s)>-1){
				return true;
			}
		}
		return false;
	}
}

