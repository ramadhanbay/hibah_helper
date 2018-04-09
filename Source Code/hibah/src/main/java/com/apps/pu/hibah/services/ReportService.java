package com.apps.pu.hibah.services;

import java.util.List;

import com.apps.pu.hibah.ui.KeyValue;

public interface ReportService {

	List<KeyValue> getSatkerByNamePaging(String direktoratName, String SatkerName, int limit, int offset);
	int countSatkerByNamePaging(String direktoratName, String SatkerName);
}
