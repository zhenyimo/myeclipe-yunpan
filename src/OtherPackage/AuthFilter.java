package OtherPackage;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class AuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpServletRequest request1 = (HttpServletRequest)request;  
	     HttpServletResponse response1 = (HttpServletResponse)response;  
	     HttpSession session = request1.getSession(); 
	     System.out.println("hellldddddd");
	    Cookie[] cookies=request1.getCookies();
		String tu=null;
		String tid=null;
		boolean hasName=true;
		if(cookies==null){
			response1.sendRedirect("http://localhost:8080/yunpan/login.html");
			return;
		}
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
		String user = (String)session.getAttribute(("_USER_INFO_LOGIN_NAME_"));
		String uid=(String)session.getAttribute("_USER_INFO_USER_ID_");
		if(user!=null&&user.equals(tu)&&uid!=null&&uid.equals(tid)){
			chain.doFilter(request, response);
		}
		else{
			response1.sendRedirect("http://localhost:8080/yunpan/login.html");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
