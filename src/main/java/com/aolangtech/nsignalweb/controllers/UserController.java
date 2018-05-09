package com.aolangtech.nsignalweb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.models.AuthorizationModel;
import com.aolangtech.nsignalweb.services.AuthorizationService;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	AuthorizationService authorizationService;
	
	@GetMapping(value = "/getAll")
	public List<AuthorizationModel> getAllUsers() {
		return authorizationService.getAllUsers();
	}
	
	@PostMapping(value = "/remove")
	public int removeUser(@RequestBody AuthorizationModel user) {
		return authorizationService.removeUserByUsername(user.getUsername());
	}
	
	@PostMapping(value = "/add")
	public AuthorizationModel addUser(@RequestBody AuthorizationModel user) {
		// insert function will setting id into user
		int count = authorizationService.addUser(user);
		if(count > 0) {
			return user;
		} else {
			return null;
		}
	}
	
	@PostMapping(value = "/updatePassword")
	@ResponseBody
	public String updatePassword(@RequestBody Map<String, String> userMap){
		String username = userMap.get("username");
		String oldPwd = userMap.get("oldPwd");
		String newPwd = userMap.get("newPwd");
		
		AuthorizationModel user = authorizationService.getUserByUsername(username);
		if(user == null || !user.getPassword().equals(oldPwd)) {
			// Authorized fail
			return "1";
		}
		
		if(authorizationService.updatePassword(username, newPwd) == 0){
			// update fail
			return "2";
		}
		
		return "0";
	}
	
	@PostMapping(value = "/updateUser")
	public int updatePasswordAdmin(@RequestBody AuthorizationModel user) {
		return authorizationService.updateUser(user);
	}
	
	@PostMapping(value = "/removeUserList")
	public int removeUserList(@RequestBody List<Long> idList) {
		return authorizationService.removeUserByIdList(idList);

	}
	
}
