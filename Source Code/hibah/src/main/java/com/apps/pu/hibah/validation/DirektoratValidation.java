package com.apps.pu.hibah.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.apps.pu.hibah.entity.Direktorat;
import com.google.common.base.Strings;

@Component
public class DirektoratValidation {
	
	public static final String NAME = "NAME";
	public static final String DESC = "DESC";
	
	
public void validate(Direktorat subject) throws ValidationException {
		
		Map<String, String> mapValidate = new HashMap<String, String>();
		
		if(Strings.isNullOrEmpty(subject.getName())) {
			mapValidate.put(NAME, "Name tidak boleh kosong");
		}
		
		if(Strings.isNullOrEmpty(subject.getDescription())) {
			mapValidate.put(DESC, "Deskripsi tidak boleh kosong");
		}
		
		if(mapValidate.size()>0) {
			throw new ValidationException(mapValidate);
		}
	}

}
