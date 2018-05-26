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

import com.apps.pu.hibah.entity.Roles;
import com.apps.pu.hibah.services.RoleService;
import com.apps.pu.hibah.validation.RoleValidation;
import com.apps.pu.hibah.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class RoleSetupComposser extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private Logger log = LoggerFactory
//			.getLogger(RoleSetupComposser.class.getName());
	
	@WireVariable(rewireOnActivate = true)
	private RoleValidation roleValidation;
	@WireVariable(rewireOnActivate = true)
	private RoleService roleServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Textbox txtRole;
	@Wire
	private Textbox txtDescription;
	@Wire
	private Textbox txtCode;
	@Wire
	private Button btnDelete;
	
	protected Roles rolePublic;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);		
		
		if(arg.containsKey(Roles.class.getName())) {
			populateData();
		}
		
		if(arg.containsKey("NEW")) {
			btnDelete.setDisabled(true);
		}
		
	}
	
	private void populateData() {
		rolePublic = (Roles) arg.get(Roles.class.getName());
		txtRole.setValue(rolePublic.getRoleName());
		txtCode.setValue(rolePublic.getRoleCode());
		txtDescription.setValue(rolePublic.getDescription());
		
	}

	@Listen("onClick=#btnSave")
	public void onSave(){
		hideError();
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -3864404895747844553L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = Integer.parseInt(event.getData().toString());
				if(resultButton == Messagebox.YES) {
					try{
						Roles role = getFinalRole(rolePublic);
						roleValidation.validate(role);
						roleServiceImpl.save(role);
						Messagebox.show(Labels.getLabel("notif.saved"), Labels.getLabel("common.title.information"), Messagebox.OK, null);						
						Executions.createComponents("view/role_inquiry.zul", getSelf().getParent(), null);
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
		
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -8260328888473933696L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = Integer.parseInt(event.getData().toString());
				
				if (resultButton == Messagebox.YES){					
					Executions.createComponents("view/role_inquiry.zul", getSelf().getParent(), null);
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
						Roles role = getFinalRole(rolePublic);
						roleServiceImpl.delete(role);
						Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
						Executions.createComponents("view/role_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();	
					}catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}

			
		});
	}
	
	protected Roles getFinalRole(Roles obj) {
		
		Roles result = new Roles();
		
		if(obj != null && obj.getIdRole() != null) {
			result.setIdRole(obj.getIdRole());
			result.setCreateDate(obj.getCreateDate());
			result.setCreateBy(obj.getCreateBy());
		}else {
			result.setCreateDate(new Date());
			result.setCreateBy("SYS");
		}
		
		result.setRoleName(txtRole.getValue());
		result.setDescription(txtDescription.getValue());
		result.setRoleCode(txtCode.getValue());		
		
		result.setUpdateDate(new Date());
		result.setUpdateBy("SYS");
		
		return result;
	}
	
	private void hideError(){
//		ErrorMessageUtil.clearErrorMessage(txtGroupType);
//		ErrorMessageUtil.clearErrorMessage(lstCompetencyType);
//		ErrorMessageUtil.clearErrorMessage(bnbProficiencyLevel);
//		ErrorMessageUtil.clearErrorMessage(errorMessage);
//		ErrorMessageUtil.clearErrorMessage(dtbFrom);
//		ErrorMessageUtil.clearErrorMessage(dtbTo);
	}
	
	private void showError(ValidationException e){
//		ErrorMessageUtil.setErrorMessage(txtGroupType, e.getMessage(CompetencyGroupValidator.COMPETENCY_GROUP_TYPE_NULL));
//		ErrorMessageUtil.setErrorMessage(txtGroupType, e.getMessage(CompetencyGroupValidator.COMPETENCY_GROUP_NAME_TAKEN));
//		ErrorMessageUtil.setErrorMessage(lstCompetencyType, e.getMessage(CompetencyGroupValidator.COMPETENCY_TYPE_NULL));
//		ErrorMessageUtil.setErrorMessage(errorMessage, e.getMessage(CompetencyGroupValidator.SEQUENCE_PASSED));
//		ErrorMessageUtil.setErrorMessage(errorMessage, e.getMessage(CompetencyGroupValidator.SEQUENCE_TAKEN));
//		ErrorMessageUtil.setErrorMessage(errorMessage, e.getMessage(CompetencyGroupValidator.SUM_OF_HAV));
//		ErrorMessageUtil.setErrorMessage(errorMessage, e.getMessage(CompetencyGroupValidator.EMPTY_COMPETENCY));
//		ErrorMessageUtil.setErrorMessage(bnbProficiencyLevel, e.getMessage(CompetencyGroupValidator.PROFICIENCY_LEVEL_NULL));
//		ErrorMessageUtil.setErrorMessage(dtbFrom, e.getMessage(VersionValidator.DATE_FROM));
//		ErrorMessageUtil.setErrorMessage(dtbTo, e.getMessage(VersionValidator.DATE_TO));
	}

}
