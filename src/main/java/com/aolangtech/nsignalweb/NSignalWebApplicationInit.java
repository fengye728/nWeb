package com.aolangtech.nsignalweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.aolangtech.nsignalweb.models.AuthorizationModel;
import com.aolangtech.nsignalweb.services.AuthorizationService;

@Component
public class NSignalWebApplicationInit implements ApplicationRunner{

	@Autowired 
	AuthorizationService authorizationService;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// create authorization table
		authorizationService.createAuthorizationTable();
		List<AuthorizationModel> userlist = authorizationService.getAllUsers();
		if(userlist.isEmpty()) {
			// add initial user
			AuthorizationModel initUser = new AuthorizationModel();
			initUser.setUsername("admin");
			initUser.setPassword("123");
			initUser.setRole("ROLE_ADMIN");
			authorizationService.addUser(initUser);
		}
	}

}
