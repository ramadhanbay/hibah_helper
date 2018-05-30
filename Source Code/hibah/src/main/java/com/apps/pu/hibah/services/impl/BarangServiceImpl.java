package com.apps.pu.hibah.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.dao.BarangDao;
import com.apps.pu.hibah.entity.Barang;
import com.apps.pu.hibah.services.BarangService;

@Service
@Transactional
public class BarangServiceImpl implements BarangService {

	@Autowired
	private BarangDao dao;
	
	public List<Barang> findBarangBykodeAndName(String kode, String name) {
		
		return dao.findBarangBykodeAndName(kode,name);
	}

	public void delete(Barang barang) {
		dao.delete(barang);
		
	}

	public void save(Barang barang) {
		dao.save(barang);
		
	}

}
