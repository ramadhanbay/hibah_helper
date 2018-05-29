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

import com.apps.pu.hibah.entity.Satker;
import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.services.SatkerService;
import com.apps.pu.hibah.ui.KeyValue;
import com.apps.pu.hibah.ui.PopupSingle;
import com.apps.pu.hibah.ui.SerializableSearchDelegate;
import com.google.common.base.Strings;

@VariableResolver(DelegatingVariableResolver.class)
public class SatkerInquiryComposser extends SelectorComposer<Window>{
	
	private static final long serialVersionUID = 1L;
	
	@Wire
	private PopupSingle bnbDirektorat;
	@Wire
	private Textbox txtSatker;
	@Wire
	private Listbox lstSatker;
	
	@WireVariable(rewireOnActivate = true)
	private ReportService reportServiceImpl;
	
	@WireVariable(rewireOnActivate = true)
	private SatkerService satkerServiceImpl;
	
	private Integer direktorat;
	private String satker;
	
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

			public void mapper(KeyValue keyValue, KeyValue data) {
				
				if(data != null) {
					keyValue.setKey(data.getKey());
					keyValue.setValue(data.getValue());
					keyValue.setDescription(data.getDescription());
				}			
				
			}
			
			public List<KeyValue> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				
				return reportServiceImpl.getDirektoratByNamePaging(searchCriteria, limit, offset);
			}
			
			public int countBySearchCriteria(String searchCriteria) {
				return reportServiceImpl.countDirektoratByNamePaging(searchCriteria);
			}
		};		
		
	}
	
	
	@Listen("onDetail= #satkerWin")
	public void onDetail(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Satker.class.getName(), event.getData());
		
		Executions.createComponents("view/satker_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = #btnNew")
	public void addNew(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("NEW", "NEW");
		Executions.createComponents("view/satker_setup.zul", getSelf().getParent(), param);
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
		direktorat = bnbDirektorat.getKeyValue() == null ? null : bnbDirektorat.getKeyValue().getKey() == null ? null : (Integer) bnbDirektorat.getKeyValue().getKey();
		satker = txtSatker.getText();
		
		return ((direktorat==null)&&Strings.isNullOrEmpty(satker));
		
	}


	protected void search() {		
		ListModelList<Satker> model = new ListModelList<Satker>(getData());
		lstSatker.setModel(model);
		lstSatker.renderAll();	
	}
	
	private List<Satker> getData() {
		return satkerServiceImpl.findSatkerByDirektorat(direktorat, satker);
	}


}
