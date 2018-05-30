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

import com.apps.pu.hibah.entity.Barang;
import com.apps.pu.hibah.entity.Satker;
import com.apps.pu.hibah.services.BarangService;
import com.apps.pu.hibah.tools.ErrorMessageUtil;
import com.apps.pu.hibah.validation.BarangValidation;
import com.apps.pu.hibah.validation.SatkerValidation;
import com.apps.pu.hibah.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class BarangSetupComposser extends SelectorComposer<Window> {

private static final long serialVersionUID = 1L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire private Textbox txtKode;
	@Wire private Textbox txtBarang;
	@Wire
	private Button btnDelete;
	
	@WireVariable(rewireOnActivate = true)
	private BarangService barangServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private BarangValidation barangValidation;
	
	private Barang barangPublic;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		if(arg.containsKey(Barang.class.getName())) {
			populateData();
		}
		
		if(arg.containsKey("NEW")) {
			btnDelete.setDisabled(true);
		}
		
		hideError();
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
						Barang barang = getFinalBarang(barangPublic);
						barangValidation.validate(barang);
						barangServiceImpl.save(barang);
						Messagebox.show(Labels.getLabel("notif.saved"), Labels.getLabel("common.title.information"), Messagebox.OK, null);						
						Executions.createComponents("view/barang_inquiry.zul", getSelf().getParent(), null);
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
					Executions.createComponents("view/barang_inquiry.zul", getSelf().getParent(), null);
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
						Barang barang = getFinalBarang(barangPublic);
						barangServiceImpl.delete(barang);
						Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
						Executions.createComponents("view/satker_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();	
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	protected Barang getFinalBarang(Barang obj) {
		
		Barang result = new Barang();
		
		if(obj != null && obj.getIdBarang() != null) {
			result.setIdBarang(obj.getIdBarang());
			result.setCreateDate(obj.getCreateDate());
			result.setCreateBy(obj.getCreateBy());
		}else {
			result.setCreateDate(new Date());
			result.setCreateBy("SYS");
		}
		
		result.setNamaBarang(txtBarang.getValue());
		result.setKodeBarang(txtKode.getValue());		
		result.setUpdateDate(new Date());
		result.setUpdateBy("SYS");
		
		return result;
	}

	private void populateData() {
		barangPublic = (Barang) arg.get(Barang.class.getName());
		txtKode.setValue(barangPublic.getKodeBarang());
		txtBarang.setValue(barangPublic.getNamaBarang());
	}
	
	private void hideError(){
		ErrorMessageUtil.clearErrorMessage(txtKode);
		ErrorMessageUtil.clearErrorMessage(txtBarang);
	}
	
	private void showError(ValidationException e){
		ErrorMessageUtil.setErrorMessage(txtKode, e.getMessage(BarangValidation.KODE));
		ErrorMessageUtil.setErrorMessage(txtBarang, e.getMessage(BarangValidation.NAME));
	}
}
