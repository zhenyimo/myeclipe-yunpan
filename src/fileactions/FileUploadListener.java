package fileactions;
import org.apache.commons.fileupload.ProgressListener;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class FileUploadListener implements ProgressListener {
	private HttpSession session;
	
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public FileUploadListener(HttpServletRequest  request){
			super();
			this.session=request.getSession();
			FileProgressState fileState=new FileProgressState();
			session.removeAttribute("state");
			session.setAttribute("state", fileState);
			System.out.println("success to create session");
	}
	
	public void update(long arg0, long arg1, int arg2) {
		// TODO Auto-generated method stub
		//System.out.println("current:"+arg0+",total:"+arg1+";"+arg2);
		FileProgressState fileState=(FileProgressState)this.session.getAttribute("state");
		fileState.setAlreadyRead(arg0);
		fileState.setTotal2Read(arg1);
		fileState.setCurrentItem(arg2);
	}

}

