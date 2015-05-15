package EndeFilePackage1;


public class Ttest {
	public static void main(String[] args)throws Exception{
		Split cutt = new Split("d:\\心得.txt","1");
		cutt.cut();
		Integration hee = new Integration("心得8.dat", "txt");
		//System.out.println("hello world1 !");
		hee.unite();
		//System.out.println("hello world3 !");
	}
}