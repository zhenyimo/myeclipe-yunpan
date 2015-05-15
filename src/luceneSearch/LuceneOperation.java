package luceneSearch;

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;

import fileactions.jsonFile;
import hdfs.HdfsDirectory;
import hdfs.hdfsOperation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class LuceneOperation {
	public static IndexWriter getIndexWriter(Path indexPath) {

		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_46);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,
				analyzer);
		Configuration conf = new Configuration();
		// Path p1 =new Path("hdfs://10.2.143.5:9090/root/myfile/my.txt");
		// Path path=new Path("hdfs://10.2.143.5:9090/root/myfile");
		try{
			HdfsDirectory directory = new HdfsDirectory(indexPath, conf);
			IndexWriter writer = new IndexWriter(directory, config);	
			return writer;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		

	}
	
	
//hdfsPath和id号的映射
/*public static int MapPath2Id(String hdfsPath){
	File f=new File("F://P2iD");
	
}*/
	

	public static boolean indexAdd(File srcTemp,Path hdfsPath,String fileName,String ext,Path indexPath){
		String temp=null;
		if(ext.equals("txt")){
			temp=FileReadFactory.readTxt(srcTemp);
		}
		else if(ext.equals("pdf")){
			try {
				temp=FileReadFactory.readPdf(srcTemp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(ext.equals("doc")){
			temp=FileReadFactory.readWord(srcTemp);
		}
		else return false;
		Document doc =new Document();
		doc.add(new StringField("fileName",fileName,Store.YES));
		doc.add(new StringField("hdfsPath",hdfsPath.toString(),Store.YES));
		doc.add(new StringField("extend",ext,Store.YES));
		
		if(temp==null){
			temp="";
		}
		TextField textField=new TextField("content",temp,Store.NO);
		doc.add(textField);
		try{
			IndexWriter writer = getIndexWriter(indexPath);
			writer.addDocument(doc);
			writer.forceMerge(1);
			writer.commit();
			System.out.println(fileName+"::"+hdfsPath.toString()+"::索引添加成功");
			writer.close();
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	
		
	}
	
	
	public static boolean indexDel(String hdfsPath,Path indexPath) {
		try{
			IndexWriter writer = getIndexWriter(indexPath);
			writer.deleteDocuments(new Term("hdfsPath",hdfsPath));// 删除指定ID的数据
			writer.forceMerge(1);// 清除已经删除的索引空间
			writer.commit();// 提交变化
			System.out.println(hdfsPath+"的数据索引已经删除成功.........");
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public static boolean queryByContent(Path hdfsPath,String queryTerm,Path indexPath,ArrayList<jsonFile> res) {
		int id=0;
		String tmpPath;
		String hp=hdfsPath.toString();
		System.out.println("本次检索内容:  " + queryTerm);
		FileStatus tmpfs;
		try{
			Configuration conf = new Configuration();
			//Path path = new Path("hdfs://localhost:9000/mo/index");
			Directory directory = new HdfsDirectory(indexPath, conf);
			IndexReader reader = DirectoryReader.open(directory);
			System.out.println("总数据量: " + reader.numDocs());
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parse = new QueryParser(Version.LUCENE_46,"content",
					new SmartChineseAnalyzer(Version.LUCENE_46));

			Query query = parse.parse(queryTerm);

			TopDocs docs = searcher.search(query, 100);
			System.out.println("本次命中结果:   " + docs.totalHits + "  条");
			for(ScoreDoc sd:docs.scoreDocs){
				id=sd.doc;
				tmpPath=reader.document(id).getField("hdfsPath").stringValue();
				if(tmpPath.startsWith(hp)){
					tmpfs=hdfsOperation.getFs().getFileStatus(new Path(tmpPath));
					res.add(new jsonFile(tmpfs));
					System.out.println(tmpPath+"::添加到结果中");
				}			
				else System.out.println(tmpPath+"::未添加");
			}
			reader.close();
			directory.close();
			System.out.println("检索完毕...............");
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		

	}
	
	
	
	
	
	
}
