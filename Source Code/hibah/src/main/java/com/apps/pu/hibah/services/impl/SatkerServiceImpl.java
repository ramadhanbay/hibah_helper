package com.apps.pu.hibah.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.dao.SatkerDao;
import com.apps.pu.hibah.entity.Satker;
import com.apps.pu.hibah.services.SatkerService;

@Service
@Transactional
public class SatkerServiceImpl implements SatkerService {
	
	@Autowired
	SatkerDao dao;

	public List<Satker> findSatkerByDirektorat(Integer idDirektorat, String satker) {
		return dao.getSatuanKerjaByDirektorat(idDirektorat, satker);
	}

	public void save(Satker satker) {
		dao.save(satker);		
	}

	public void delete(Satker satker) {
		dao.delete(satker);		
	}

}
