package com.apps.pu.hibah;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.entity.Roles;
import com.apps.pu.hibah.services.RoleService;


@VariableResolver(DelegatingVariableResolver.class)
public class RoleInquiryComposser extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire private Textbox txtRoleName;
	@Wire private Listbox lstRole;
	
	@WireVariable(rewireOnActivate = true)
	private RoleService roleServiceImpl;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
		
	}
	
	@Listen("onClick=#btnFind")
	public void onFind(){
		if(txtRoleName.getText().equals("")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = Integer.parseInt(event.getData().toString());
					if(resultButton == Messagebox.YES) {
						search();
					}
				}
			});
		} else {
			search();
		}
	}

	protected void search() {		
		ListModelList<Roles> model = new ListModelList<Roles>(getData());
		lstRole.setModel(model);
		lstRole.renderAll();	
	}

	private List<Roles> getData() {
		String roleName = txtRoleName.getText();
		return roleServiceImpl.findRoleLikeName(roleName);
	}

}
