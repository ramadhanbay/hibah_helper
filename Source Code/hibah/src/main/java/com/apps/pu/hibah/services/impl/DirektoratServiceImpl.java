package com.apps.pu.hibah.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.dao.DirektoratDao;
import com.apps.pu.hibah.entity.Direktorat;
import com.apps.pu.hibah.services.DirektoratService;

@Service
@Transactional
public class DirektoratServiceImpl implements DirektoratService {

	@Autowired
	DirektoratDao dao;
	
	@Override
	public List<Direktorat> findDirektoratLikeName(String dirName) {
		// TODO Auto-generated method stub
		return dao.getDirektoratLikeName(dirName);
	}

	public void save(Direktorat direktorat) {
		dao.save(direktorat);
		
	}

	public void delete(Direktorat direktorat) {
		dao.delete(direktorat);
		
	}

}
