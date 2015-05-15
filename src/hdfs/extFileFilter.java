package hdfs;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

public class extFileFilter implements PathFilter {
	private String[] regexs;
	
	
	public extFileFilter(String[] regexs){
		this.regexs=regexs;
	}
	@Override
	public boolean accept(Path arg0) {
		// TODO Auto-generated method stub
		for(String e:regexs){
			if(arg0.toString().matches(e)){
				return true;
			}
		}
		return false;
	}

}
