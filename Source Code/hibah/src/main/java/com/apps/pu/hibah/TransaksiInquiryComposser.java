package com.apps.pu.hibah;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class TransaksiInquiryComposser extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
	}
	
	@Listen("onDetail= #satkerWin")
	public void onDetail(ForwardEvent event){
		
	}
	
	@Listen("onClick = #btnNew")
	public void addNew(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("NEW", "NEW");
		Executions.createComponents("view/transaksi.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
}
