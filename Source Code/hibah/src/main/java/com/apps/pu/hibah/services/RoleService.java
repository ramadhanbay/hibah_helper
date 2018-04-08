package com.apps.pu.hibah.services;

import java.util.List;

import com.apps.pu.hibah.entity.Roles;

public interface RoleService {

	List<Roles> findRoleLikeName(String roleName);

	void save(Roles role);

	void delete(Roles role);
}
