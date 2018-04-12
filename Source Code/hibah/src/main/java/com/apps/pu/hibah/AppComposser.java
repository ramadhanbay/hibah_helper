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
		
		//===== HOME ========
		Navitem homeItem = new Navitem();
		homeItem.setLabel("Home");
		homeItem.setIconSclass("z-icon-home");
		homeItem.setId(PageEnum.HMPG.getValue());
		homeItem.setParent(navbar);
		
		//===== INPUT DATA ========
		Nav nav = new Nav();
		nav.setLabel("Input Data");
		nav.setIconSclass("z-icon-group");	
		nav.setBadgeText("3");
		nav.setParent(navbar);
		
		Navitem itemInputBangkim = new Navitem();
		itemInputBangkim.setLabel(PageEnum.INPDIR.getValue());
		itemInputBangkim.setId(PageEnum.INPDIR.getValue());
		itemInputBangkim.setParent(nav);
		
		Navitem itemSatker = new Navitem();
		itemSatker.setLabel("Satuan Kerja");
		itemSatker.setId(PageEnum.INPSAT.getValue());
		itemSatker.setParent(nav);
		
		Navitem itemBarang = new Navitem();
		itemBarang.setLabel(PageEnum.INPBAR.getValue());
		itemBarang.setId(PageEnum.INPBAR.getValue());
		itemBarang.setParent(nav);
		
		//===== Report ========
		Nav navReport = new Nav();
		navReport.setLabel("Laporan");
		navReport.setIconSclass("z-icon-book");
		navReport.setBadgeText("2");
		navReport.setParent(navbar);
		
		Navitem itemProgress = new Navitem();
		itemProgress.setLabel("Progress");
		itemProgress.setId(PageEnum.PRGREP.getValue());
		itemProgress.setParent(navReport);
		
		Navitem itemCategories = new Navitem();
		itemCategories.setLabel("Entity");
		itemCategories.setId(PageEnum.CATREP.getValue());
		itemCategories.setParent(navReport);
		
		//===== ADMIN ========
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
			contentInclude.setSrc("greeting.zul");
		}else if (event.getTarget().getId() == PageEnum.RLADM.getValue()) {
			contentInclude.setSrc("view/role_inquiry.zul");
		}else if (event.getTarget().getId() == PageEnum.PRGREP.getValue()) {
			contentInclude.setSrc("view/report_progress.zul");
		}else if (event.getTarget().getId() == PageEnum.CATREP.getValue()) {
			contentInclude.setSrc("view/report_entity.zul");
		}else if (event.getTarget().getId() == PageEnum.INPDIR.getValue()) {
			contentInclude.setSrc("view/direktorat_inquiry.zul");
		}else if (event.getTarget().getId() == PageEnum.INPSAT.getValue()) {
			contentInclude.setSrc("view/satker_inquiry.zul");
		}
		
		else {
			contentInclude.setSrc("greeting.zul");
		}
	}

}
