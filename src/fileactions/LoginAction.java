package fileactions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import OtherPackage.UserCheck;

import com.opensymphony.xwork2.Action;

public class LoginAction implements Action {
	private String username;
	private String pwd;
	private String msg;
	
	
	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String login() throws Exception {
		// TODO Auto-generated method stub
		if(username==null||username.equals("")||pwd==null||pwd.equals("")){
			this.msg="用户或密码为空";
			return Action.INPUT;
		}
		else{
			UserCheck chk=new UserCheck();
			if(!chk.check(username, pwd)){
				this.msg="用户或密码错误";
				return Action.INPUT;
			}
			else {
				HttpSession session=ServletActionContext.getRequest().getSession(true);
				session.setAttribute("_USER_INFO_LOGIN_NAME_", this.username);
				session.setAttribute("_USER_INFO_USER_ID_",session.getId());
				session.setMaxInactiveInterval(30);
				//设置cookie
				ServletActionContext.getResponse().addCookie(new Cookie("_USER_INFO_LOGIN_NAME_", this.username));
				ServletActionContext.getResponse().addCookie(new Cookie("_USER_INFO_USER_ID_",session.getId()));
				return Action.SUCCESS; 
			}
		}
	}


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
