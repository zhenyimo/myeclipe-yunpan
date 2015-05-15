package fileactions;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		Cookie[] cookies=ServletActionContext.getRequest().getCookies();
		String tu=null;
		String tid=null;
		boolean hasName=true;
		for(Cookie c:cookies){
			if(hasName&&c.getName().equals("_USER_INFO_LOGIN_NAME_")){
				tu=c.getValue();
				hasName=false;
			}
			if(c.getName().equals("_USER_INFO_USER_ID_")){
				tid=c.getValue();
				break;
			}
		}
		String user = (String)ActionContext.getContext().getSession().get("_USER_INFO_LOGIN_NAME_");
		String uid=(String)ActionContext.getContext().getSession().get("_USER_INFO_USER_ID_");
		if(user!=null&&user.equals(tu)&&uid!=null&&uid.equals(tid)){
			return arg0.invoke();
		}
		return Action.LOGIN;
	}

}
