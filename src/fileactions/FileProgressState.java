package fileactions;

public class FileProgressState {
	private long alreadyRead=0L;
	private long total2Read=0L;
	private int currentItem=0;
	private int rate=0;
	public FileProgressState(){
	}
	public FileProgressState(long alreadyRead,long total2Read,int rate){
			this.alreadyRead=alreadyRead;
			this.total2Read=total2Read;
			this.rate=rate;
	}
	
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public long getAlreadyRead() {
		return alreadyRead;
	}
	public void setAlreadyRead(long alreadyRead) {
		this.alreadyRead = alreadyRead;
	}
	public long getTotal2Read() {
		return total2Read;
	}
	public void setTotal2Read(long total2Read) {
		this.total2Read = total2Read;
	}
	public int getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(int currentItem) {
		this.currentItem = currentItem;
	}

	
}

