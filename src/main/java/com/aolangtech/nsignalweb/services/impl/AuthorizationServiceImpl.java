/**
 * 
 */
/**
 * @author SEELE
 *
 */
package com.aolangtech.nsignalweb.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.aolangtech.nsignalweb.mappers.option.AuthorizationModelMapper;
import com.aolangtech.nsignalweb.models.AuthorizationModel;
import com.aolangtech.nsignalweb.services.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	AuthorizationModelMapper authorizationModelMapper;
	
	@Override
	public List<AuthorizationModel> getAllUsers() {
		return authorizationModelMapper.selectAll();
	}

	@Override
	public AuthorizationModel getUserByUsername(String username) {
		return authorizationModelMapper.selectByUsername(username);
	}
	
	@Transactional(value = "optionTransactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public int updatePassword(String username, String password) {
		if(username == null || password == null) {
			return 0;
		}
		return authorizationModelMapper.updatePassword(username, password);
	}


	@Transactional(value = "optionTransactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public int removeUserByUsername(String username) {
		if(username == null) {
			return 0;
		}
		return authorizationModelMapper.softDeleteByUsername(username);
	}

	@Transactional(value = "optionTransactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public int addUser(AuthorizationModel user) {
		if(user == null || user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
			return 0;
		}
		return authorizationModelMapper.insert(user);
	}

	@Transactional(value = "optionTransactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	@Override
	public int removeUserByIdList(List<Long> idList) {
		int count = 0;
		for(Long id : idList) {
			count += authorizationModelMapper.softDeleteById(id);
		}
		return count;
	}

	@Override
	public int updateUser(AuthorizationModel user) {
		if(user == null || user.getId() == null || user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
			return 0;
		}
		return authorizationModelMapper.updateUser(user);
	}
	
}