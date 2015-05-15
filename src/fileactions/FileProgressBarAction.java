package fileactions;

import com.opensymphony.xwork2.Action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

public class FileProgressBarAction implements Action {
	private FileProgressState state;
	private boolean success=true;
	private Object resultStr;
	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}



	public Object getResultStr() {
		return resultStr;
	}


	public void setResultStr(Object resultStr) {
		this.resultStr = resultStr;
	}


	public FileProgressState getState() {
		return state;
	}


	public void setState(FileProgressState state) {
		this.state = state;
	}


	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpSession session=ServletActionContext.getRequest().getSession();
		HttpServletResponse response=   ServletActionContext.getResponse();
		response.setContentType("text/html; charset=utf-8"); 
		state=(FileProgressState)session.getAttribute("state");
		if(state==null){
			state=new FileProgressState();
			state.setCurrentItem(0);
		}
		else{
			Double a=Double.parseDouble(state.getAlreadyRead()+"");
			Double b=Double.parseDouble(state.getTotal2Read()+"");			
			double result=a/b*100;
			state.setRate((int)result);
			resultStr=JSON.toJSON(state);
			System.out.println("action:"+state.getRate());
		}
		return SUCCESS;
	}

}
