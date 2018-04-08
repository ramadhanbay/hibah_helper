package com.apps.pu.hibah.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.apps.pu.hibah.entity.Roles;
import com.google.common.base.Strings;

@Component
public class RoleValidation {
	
	public static final String ROLE = "ROLE";
	public static final String ROLE_DESC = "ROLE_DESC";
	
	public void validate(Roles subject) throws ValidationException {
		
		Map<String, String> mapValidate = new HashMap<String, String>();
		
		if(Strings.isNullOrEmpty(subject.getRoleName())) {
			mapValidate.put(ROLE, "Role tidak boleh kosong");
		}
		
		if(Strings.isNullOrEmpty(subject.getDescription())) {
			mapValidate.put(ROLE_DESC, "Deskripsi tidak boleh kosong");
		}
		
		if(mapValidate.size()>0) {
			throw new ValidationException(mapValidate);
		}
	}

}
