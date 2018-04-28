package com.aolangtech.nsignalweb.mappers;

import java.util.List;

import com.aolangtech.nsignalweb.models.AuthorizationModel;

public interface AuthorizationModelMapper {

	List<AuthorizationModel> selectAll();
	
	int softDeleteByUsername(String username);
	
	int insert(AuthorizationModel user);
	
	int updatePassword(AuthorizationModel user);
}
