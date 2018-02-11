package com.apps.pu.hibah;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.obj.UserCredential;
import com.apps.pu.hibah.services.LoginService;

@VariableResolver(DelegatingVariableResolver.class)
public class LoginComposser  extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire private Textbox user;
	@Wire private Textbox password;
	
	@WireVariable(rewireOnActivate = true)
	private LoginService loginServiceImpl;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
		
	}
	
	@Listen("onClick=#btnLogin")
	public void onSave(){
		
		if(loginServiceImpl.isLoginSuccess(user.getText(), password.getText())) {
			Session sess = Sessions.getCurrent();
			
			UserCredential uCred = loginServiceImpl.getUserCred(user.getText());
			
			sess.setAttribute("userCred", uCred);
			
			
			Executions.sendRedirect("apps.zul");
		}else {
			Messagebox.show("Gagal Login. User/Password tidak cocok", "Password salah", Messagebox.OK, null, null);
		}		
	}

}
