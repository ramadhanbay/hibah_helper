package com.apps.pu.hibah.services;

import com.apps.pu.hibah.obj.UserCredential;

public interface LoginService {

	boolean isLoginSuccess(String username, String password);

	UserCredential getUserCred(String text);
}
