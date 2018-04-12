package com.apps.pu.hibah.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.dao.ReportDao;
import com.apps.pu.hibah.services.ReportService;
import com.apps.pu.hibah.ui.KeyValue;

@Service
@Transactional
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportDao dao;

	@Override
	public List<KeyValue> getSatkerByNamePaging(String direktoratName, String SatkerName, int limit, int offset) {
		
		return dao.getSatkerByNamePaging(direktoratName, SatkerName, limit, offset);
	}

	@Override
	public int countSatkerByNamePaging(String direktoratName, String SatkerName) {
		// TODO Auto-generated method stub
		return dao.countSatkerByNamePaging(direktoratName, SatkerName);
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return dao.getConnection();
	}

	@Override
	public List<KeyValue> getDirektoratByNamePaging(String searchCriteria, int limit, int offset) {
		// TODO Auto-generated method stub
		return dao.getDirektoratByNamePaging(searchCriteria, limit, offset);
	}

	@Override
	public int countDirektoratByNamePaging(String searchCriteria) {
		// TODO Auto-generated method stub
		return dao.countDirektoratByNamePaging(searchCriteria);
	}
	
	

}
