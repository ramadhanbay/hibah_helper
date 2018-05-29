package com.apps.pu.hibah;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.apps.pu.hibah.entity.Satker;
import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.services.SatkerService;
import com.apps.pu.hibah.tools.ErrorMessageUtil;
import com.apps.pu.hibah.ui.KeyValue;
import com.apps.pu.hibah.ui.PopupSingle;
import com.apps.pu.hibah.ui.SerializableSearchDelegate;
import com.apps.pu.hibah.validation.SatkerValidation;
import com.apps.pu.hibah.validation.ValidationException;


@VariableResolver(DelegatingVariableResolver.class)
public class SatkerSetupComposser extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Textbox txtDescription;
	@Wire
	private Textbox txtSatker;
	@Wire
	private Button btnDelete;
	@Wire
	private PopupSingle bnbDirektorat;
	
	@WireVariable(rewireOnActivate = true)
	private ReportService reportServiceImpl;	
	@WireVariable(rewireOnActivate = true)
	private SatkerService satkerServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private SatkerValidation satkerValidation;
	
	private Satker satkerPublic;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		bnbDirektorat.setTitle(Labels.getLabel("report.Direktorat"));
		bnbDirektorat.setSearchText("Direktorat");
		bnbDirektorat.setHeaderLabel("Direktorat");
		bnbDirektorat.setReadonly(true);
		bnbDirektorat.setSearchDelegate(eventSearchDir());
		
		if(arg.containsKey(Satker.class.getName())) {
			populateData();
		}
		
		if(arg.containsKey("NEW")) {
			btnDelete.setDisabled(true);
		}
	}
	
	@Listen("onClick=#btnSave")
	public void onSave(){
		hideError();
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -3864404895747844553L;

			public void onEvent(Event event) throws Exception {
				int resultButton = Integer.parseInt(event.getData().toString());
				if(resultButton == Messagebox.YES) {
					try{
						Satker dir = getFinalDir(satkerPublic);
						satkerValidation.validate(dir);
						satkerServiceImpl.save(dir);
						Messagebox.show(Labels.getLabel("notif.saved"), Labels.getLabel("common.title.information"), Messagebox.OK, null);						
						Executions.createComponents("view/setup_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();	
						
					}catch (ValidationException e) {
						showError(e);
//						log.error(e.getMessage());
						e.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();
//						log.error(e.getMessage());
					}
					
				}
				
			}

		});
	}
	
	@Listen("onClick = #btnCancel")
	public void onCancel(){
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), 
				Labels.getLabel("common.confirmationTitle"), 
				Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				int resultButton = Integer.parseInt(event.getData().toString());
				
				if (resultButton == Messagebox.YES){					
					Executions.createComponents("view/satker_inquiry.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}

		});
	}
	
	@Listen("onClick=#btnDelete")
	public void onDelete(){
		
	}
	
	private void populateData() {
		satkerPublic = (Satker) arg.get(Satker.class.getName());
		txtSatker.setValue(satkerPublic.getName());
		txtDescription.setValue(satkerPublic.getDescription());
		
		KeyValue kv = new KeyValue();
		kv.setKey(satkerPublic.getDirektorat().getIdDirektorat());
		kv.setValue(satkerPublic.getDirektorat().getName());
		kv.setDescription(satkerPublic.getDirektorat().getDescription());
		bnbDirektorat.setRawValue(kv);
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
	
	private void hideError(){
		ErrorMessageUtil.clearErrorMessage(txtSatker);
		ErrorMessageUtil.clearErrorMessage(txtDescription);
		ErrorMessageUtil.clearErrorMessage(bnbDirektorat);
	}
	
	private void showError(ValidationException e){
		ErrorMessageUtil.setErrorMessage(txtSatker, e.getMessage(SatkerValidation.NAME));
		ErrorMessageUtil.setErrorMessage(txtDescription, e.getMessage(SatkerValidation.DESC));
		ErrorMessageUtil.setErrorMessage(bnbDirektorat, e.getMessage(SatkerValidation.DIREKTORAT));
	}
	
	protected Satker getFinalDir(Satker obj) {
		
		Satker result = new Satker();
		
		
		if(obj != null && obj.getIdSatker() != null) {
			result.setIdSatker(obj.getIdSatker());
			result.setCreateDate(obj.getCreateDate());
			result.setCreateBy(obj.getCreateBy());
		}else {
			result.setCreateDate(new Date());
			result.setCreateBy("SYS");
		}
		
		result.setName(txtSatker.getValue());
		result.setDescription(txtDescription.getValue());		
		result.setUpdateDate(new Date());
		result.setUpdateBy("SYS");
		
		
		return result;
	}

}
