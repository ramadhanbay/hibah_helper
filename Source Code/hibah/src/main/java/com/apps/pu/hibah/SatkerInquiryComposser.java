package com.apps.pu.hibah;

import java.util.List;

import org.zkoss.util.resource.Labels;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.entity.Direktorat;
import com.apps.pu.hibah.entity.Satker;
import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.ui.KeyValue;
import com.apps.pu.hibah.ui.PopupSingle;
import com.apps.pu.hibah.ui.SerializableSearchDelegate;

@VariableResolver(DelegatingVariableResolver.class)
public class SatkerInquiryComposser extends SelectorComposer<Window>{
	
	private static final long serialVersionUID = 1L;
	
	@Wire
	private PopupSingle bnbDirektorat;
	@Wire
	private Textbox txtSatker;
	
	@WireVariable(rewireOnActivate = true)
	private ReportService reportServiceImpl;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
		
		
		bnbDirektorat.setTitle(Labels.getLabel("report.Direktorat"));
		bnbDirektorat.setSearchText("Direktorat");
		bnbDirektorat.setHeaderLabel("Direktorat");
		bnbDirektorat.setReadonly(true);
		bnbDirektorat.setSearchDelegate(eventSearchDir());
		
	}
	
	
	private SerializableSearchDelegate<KeyValue> eventSearchDir() {
		
		return new SerializableSearchDelegate<KeyValue>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				
				if(data != null) {
					keyValue.setKey(data.getKey());
					keyValue.setValue(data.getValue());
					keyValue.setDescription(data.getDescription());
				}			
				
			}
			
			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				
				return reportServiceImpl.getDirektoratByNamePaging(searchCriteria, limit, offset);
			}
			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return reportServiceImpl.countDirektoratByNamePaging(searchCriteria);
			}
		};		
		
	}
	
	
	@Listen("onDetail= #satkerWin")
	public void onDetail(ForwardEvent event){
		
	}
	
	@Listen("onClick = #btnNew")
	public void addNew(){
		
	}
	
	@Listen("onClick=#btnFind")
	public void onFind(){
		if(txtSatker.getText().equals("")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = Integer.parseInt(event.getData().toString());
					if(resultButton == Messagebox.YES) {
//						search();
					}
				}
			});
		} else {
//			search();
		}
	}
	
//	protected void search() {		
//		ListModelList<Satker> model = new ListModelList<Satker>(getData());
//		lstDir.setModel(model);
//		lstDir.renderAll();	
//	}
//	
//	private List<Direktorat> getData() {
//		String dirName = txtDirName.getText();
//		return direktoratServiceImpl.findDirektoratLikeName(dirName);
//	}


}
