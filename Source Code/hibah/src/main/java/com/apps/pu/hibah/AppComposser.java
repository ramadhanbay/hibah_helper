package com.apps.pu.hibah;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.tools.PageEnum;

@VariableResolver(DelegatingVariableResolver.class)
public class AppComposser extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire private Navbar navbar;
	@Wire private Include contentInclude;
	@Wire private Label lblUsername;
	@Wire private A logout;
	@Wire private Label lblLastLogin;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
		
		initPage();
	}

	private void initPage() {
		// TODO Auto-generated method stub
		
		Navitem homeItem = new Navitem();
		homeItem.setLabel("Home");
		homeItem.setIconSclass("z-icon-home");
		homeItem.setId(PageEnum.HMPG.getValue());
		homeItem.setParent(navbar);
		
		Nav nav = new Nav();
		nav.setLabel("Input Data");
		nav.setIconSclass("z-icon-group");
		
		nav.setParent(navbar);
		
		Nav navAdmin = new Nav();
		navAdmin.setLabel("Administrasi");
		navAdmin.setIconSclass("z-icon-star");
		navAdmin.setBadgeText("2");		
		navAdmin.setParent(navbar);
		
		Navitem itemAdmin = new Navitem();
		itemAdmin.setLabel("Users");
		itemAdmin.setId(PageEnum.USRADM.getValue());
		itemAdmin.setParent(navAdmin);
		
		Navitem ruleAdmin = new Navitem();
		ruleAdmin.setLabel("Roles");
		ruleAdmin.setId(PageEnum.RLADM.getValue());
		ruleAdmin.setParent(navAdmin);
		
		contentInclude.setSrc("greeting.zul");
	}
	
	@Listen("onClick = navitem")
	public void onItemSelect(Event event){
		if(event.getTarget().getId() == PageEnum.HMPG.getValue()) {
			contentInclude.setSrc("greeting.zul");
		}else if(event.getTarget().getId() == PageEnum.USRADM.getValue()) {
			contentInclude.setSrc("index.zul");
		}else if (event.getTarget().getId() == PageEnum.RLADM.getValue()) {
			contentInclude.setSrc("view/role_inquiry.zul");
		}
	}

}
