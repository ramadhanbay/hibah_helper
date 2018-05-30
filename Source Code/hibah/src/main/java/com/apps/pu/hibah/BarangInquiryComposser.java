package com.apps.pu.hibah;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
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

import com.apps.pu.hibah.entity.Barang;
import com.apps.pu.hibah.services.BarangService;
import com.google.common.base.Strings;

@VariableResolver(DelegatingVariableResolver.class)
public class BarangInquiryComposser extends SelectorComposer<Window>{

private static final long serialVersionUID = 1L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire private Textbox txtKode;
	@Wire private Textbox txtBarang;
	@Wire private Listbox lstBarang;
	
	@WireVariable(rewireOnActivate = true)
	private BarangService barangServiceImpl;
	
	private String kode;
	private String name;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	@Listen("onDetail= #satkerWin")
	public void onDetail(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Barang.class.getName(), event.getData());
		
		Executions.createComponents("view/barang_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = #btnNew")
	public void addNew(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("NEW", "NEW");
		Executions.createComponents("view/barang_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick=#btnFind")
	public void onFind(){
		if(isAllEmpty()) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = 1L;
				
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

	private boolean isAllEmpty() {
		kode = txtKode.getText();
		name = txtBarang.getText();
		
		return ((Strings.isNullOrEmpty(kode))&&(Strings.isNullOrEmpty(name)));
	}
	
	protected void search() {		
		ListModelList<Barang> model = new ListModelList<Barang>(getData());
		lstBarang.setModel(model);
		lstBarang.renderAll();	
	}

	private List<Barang> getData() {
		return barangServiceImpl.findBarangBykodeAndName(kode,name);
	}
	
}
