package com.apps.pu.hibah.obj;

import java.util.Date;
import java.util.List;

import com.apps.pu.hibah.entity.Roles;

public class UserCredential {
	
	public String username;
	public List<Roles> roles;
	public Date lastLogin;
	
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
