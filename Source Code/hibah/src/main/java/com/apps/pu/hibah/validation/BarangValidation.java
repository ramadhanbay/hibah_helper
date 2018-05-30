package com.apps.pu.hibah.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.apps.pu.hibah.entity.Barang;
import com.google.common.base.Strings;


@Component
public class BarangValidation {
	
	public static final String NAME = "NAME";
	public static final String KODE = "KODE";

	public void validate(Barang subject) throws ValidationException {
		Map<String, String> mapValidate = new HashMap<String, String>();
		
		if(Strings.isNullOrEmpty(subject.getKodeBarang())) {
			mapValidate.put(KODE, "Kode Barang tidak boleh kosong");
		}
		
		if(Strings.isNullOrEmpty(subject.getNamaBarang())) {
			mapValidate.put(NAME, "Nama Barang tidak boleh kosong");
		}
		
		if (mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}
	}
	
}
