package luceneSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

public class FileReadFactory {
	
	 //word
	 public static String readWord(File srcTemp) {
	        StringBuffer content=new StringBuffer("");// 文档内容
	        try {

	            HWPFDocument doc = new HWPFDocument(new FileInputStream(srcTemp));
	            Range range = doc.getRange();
	            int paragraphCount = range.numParagraphs();// 段落
	            for (int i = 0; i < paragraphCount; i++) {// 遍历段落读取数据
	                Paragraph pp = range.getParagraph(i);
	                content.append(pp.text());
	            }

	        } catch (Exception e) {

	        }
	        return content.toString().trim();
	    }
	 
	 //pdf
	 public static String readPdf(File srcTemp) {
		 try{
			 StringBuffer content = new StringBuffer("");// 文档内容
		        FileInputStream fis = new FileInputStream(srcTemp);
		        PDFParser p = new PDFParser(fis);
		        p.parse();
		        PDFTextStripper ts = new PDFTextStripper();
		        content.append(ts.getText(p.getPDDocument()));
		        fis.close();
		        return content.toString().trim();
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	       
	    }
	 
	 //txt
	 public static String readTxt(File srcTemp) {
	        StringBuffer content = new StringBuffer("");// 文档内容
	        try {
	            FileReader reader = new FileReader(srcTemp);
	            BufferedReader br = new BufferedReader(reader);
	            String s1 = null;

	            while ((s1 = br.readLine()) != null) {
	                content.append(s1 + "\r");
	            }
	            br.close();
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return content.toString().trim();
	    }
}
