package OtherPackage;

public class UserCheck {
	private static String username="mo";
	private static String password="123";
	public UserCheck(){
	}
	public boolean check(String name,String pwd){
		if(name.equals(username)&&password.equals(password)){
			return true;
		}
		else return false;
	}
}
