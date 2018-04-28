package com.aolangtech.nsignalweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.mappers.AuthorizationModelMapper;
import com.aolangtech.nsignalweb.models.AuthorizationModel;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	AuthorizationModelMapper authorizationModelMapper;
	
	@GetMapping(value = "/getAll")
	public List<AuthorizationModel> getAllUsers() {
		return authorizationModelMapper.selectAll();
	}
	
	@PostMapping(value = "/remove")
	public int removeUser(AuthorizationModel user) {
		return authorizationModelMapper.softDeleteByUsername(user.getUsername());
	}
	
	@PostMapping(value = "/add")
	public AuthorizationModel addUser(AuthorizationModel user) {
		// insert function will setting id into user
		int count = authorizationModelMapper.insert(user);
		if(count > 0) {
			return user;
		} else {
			return null;
		}
		
	}
	
}
