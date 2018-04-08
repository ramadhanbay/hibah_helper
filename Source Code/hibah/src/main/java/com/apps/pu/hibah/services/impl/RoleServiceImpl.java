package com.apps.pu.hibah.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.dao.RoleDao;
import com.apps.pu.hibah.entity.Roles;
import com.apps.pu.hibah.services.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleDao dao;

	@Override
	public List<Roles> findRoleLikeName(String roleName) {
		// TODO Auto-generated method stub
		return dao.getRolesLikeName(roleName);
	}

	@Override
	public void save(Roles role) {
		
		dao.save(role);
		
	}

	@Override
	public void delete(Roles role) {
		// TODO Auto-generated method stub
		dao.delete(role);
	}

}
