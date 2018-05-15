package com.aolangtech.nsignalweb.mappers.option;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aolangtech.nsignalweb.models.AuthorizationModel;

public interface AuthorizationModelMapper {

	List<AuthorizationModel> selectAll();
	
	int softDeleteByUsername(String username);
	
	int softDeleteById(Long id);
	
	int insert(AuthorizationModel user);
	
	int updatePassword(@Param("username") String username, @Param("password") String password);
	
	AuthorizationModel selectByUsername(String username);
	
	int updateUser(AuthorizationModel user);
} 
