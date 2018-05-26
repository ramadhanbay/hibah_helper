package com.apps.pu.hibah;

import java.util.Date;
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

import com.apps.pu.hibah.entity.Direktorat;
import com.apps.pu.hibah.services.DirektoratService;
import com.apps.pu.hibah.tools.ErrorMessageUtil;
import com.apps.pu.hibah.validation.DirektoratValidation;
import com.apps.pu.hibah.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class DirektoratSetupComposser extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private DirektoratValidation direktoratValidation;
	@WireVariable(rewireOnActivate = true)
	private DirektoratService direktoratServiceImpl;
	
	@Wire
	private Textbox txtName;	
	@Wire
	private Textbox txtDescription;
	@Wire
	private Button btnDelete;
	
	private Direktorat direktoratPublic;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
		
		if(arg.containsKey(Direktorat.class.getName())) {
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
						Direktorat dir = getFinalDir(direktoratPublic);
						direktoratValidation.validate(dir);
						direktoratServiceImpl.save(dir);
						Messagebox.show(Labels.getLabel("notif.saved"), Labels.getLabel("common.title.information"), Messagebox.OK, null);						
						Executions.createComponents("view/direktorat_inquiry.zul", getSelf().getParent(), null);
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
					Executions.createComponents("view/direktorat_inquiry.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}

		});
	}
	
	@Listen("onClick=#btnDelete")
	public void onDelete(){
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try{
						Direktorat dir = getFinalDir(direktoratPublic);
						direktoratServiceImpl.delete(dir);
						Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
						Executions.createComponents("view/direktorat_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();	
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	protected Direktorat getFinalDir(Direktorat obj) {
		
		Direktorat result = new Direktorat();
		
		if(obj != null && obj.getIdDirektorat() != null) {
			result.setIdDirektorat(obj.getIdDirektorat());
			result.setCreateDate(obj.getCreateDate());
			result.setCreateBy(obj.getCreateBy());
		}else {
			result.setCreateDate(new Date());
			result.setCreateBy("SYS");
		}
		
		result.setName(txtName.getValue());
		result.setDescription(txtDescription.getValue());
		
		result.setUpdateDate(new Date());
		result.setUpdateBy("SYS");
		
		return result;
	}
	
	private void populateData() {
		direktoratPublic = (Direktorat) arg.get(Direktorat.class.getName());
		txtName.setValue(direktoratPublic.getName());
		txtDescription.setValue(direktoratPublic.getDescription());		
	}
	
	private void hideError(){
		ErrorMessageUtil.clearErrorMessage(txtName);
		ErrorMessageUtil.clearErrorMessage(txtDescription);
	}
	
	private void showError(ValidationException e){
		ErrorMessageUtil.setErrorMessage(txtName, e.getMessage(DirektoratValidation.NAME));
		ErrorMessageUtil.setErrorMessage(txtDescription, e.getMessage(DirektoratValidation.DESC));
	}

}
