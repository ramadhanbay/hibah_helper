package com.apps.pu.hibah.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.pu.hibah.dao.LoginDao;
import com.apps.pu.hibah.entity.Roles;
import com.apps.pu.hibah.entity.Users;
import com.apps.pu.hibah.obj.UserCredential;
import com.apps.pu.hibah.services.LoginService;
import com.google.common.base.Strings;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Override
	public boolean isLoginSuccess(String username, String password) {
		
		if(Strings.isNullOrEmpty(username)) {
			return false;
		}
		
		if(Strings.isNullOrEmpty(password)) {
			return false;
		}
		
		Users objUser = loginDao.getUser(username, password);
		
		if(objUser!=null) {
			return true;
		}
		
		return false;
	}

	@Override
	public UserCredential getUserCred(String userName) {
		// TODO Auto-generated method stub
		UserCredential userCred = new UserCredential();
		userCred.setUsername(userName);
		
		List<Roles> roles = loginDao.getRolesByUsername(userName);
		
		userCred.setRoles(roles);
		
		return userCred;
	}

}
