/**
 * 
 */
/**
 * @author SEELE
 *
 */
package com.aolangtech.nsignalweb.services;

import java.util.List;

import com.aolangtech.nsignalweb.models.AuthorizationModel;

public interface AuthorizationService {
	
	List<AuthorizationModel> getAllUsers();
	
	int removeUserByUsername(String username);
	
	int removeUserByIdList(List<Long> idList);
	
	int addUser(AuthorizationModel user);

	int updatePassword(String username, String password);
	
	AuthorizationModel getUserByUsername(String username);
	
	int updateUser(AuthorizationModel user);
	
	int createAuthorizationTable();
}