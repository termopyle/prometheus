package org.kobic.omics.ksso.service;

import javax.annotation.Resource;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.ksso.thrift.client.ClientHandler;
import org.kobic.omics.ksso.dao.UserDao;
import org.kobic.omics.ksso.mapper.UserMapper;
import org.kobic.omics.ksso.vo.UserVo;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserService implements UserDao {
	
	@Resource(name = "userMapper")
	private UserMapper userMapper;

	private ClientHandler handler = new ClientHandler();
	@Override
	public String kssoUserRegister(UserVo userVo) throws TException {
		// TODO Auto-generated method stub
		
		UserModel userModel = new UserModel();
		userModel.setUser_name(userVo.getUserName());
		userModel.setUser_id(userVo.getUserId());
		userModel.setPassword(userVo.getPassword());
		userModel.setOrganization(userVo.getOrganization());
		userModel.setPosition(userVo.getPosition());
		userModel.setEmail_adress(userVo.getEmailAdress());
		userModel.setRegdate(userVo.getRegdate());
		userModel.setTel(userVo.getTel());
		userModel.setHp(userVo.getHp());
		userModel.setFax(userVo.getFax());
		userModel.setAdmin(false);
		userModel.setIdentity_number(userVo.getIdentity_number());
		
		String userId = null; 
				
		try {
			
			userId = handler.addUser(userModel);
		} catch (TApplicationException e) {
			
			userId = null;
		}
		
		return userId;
	}

	@Override
	public boolean login(String userId, String passwd) throws TException {
		// TODO Auto-generated method stub
		return handler.login(userId, passwd);
	}

	@Override
	public boolean isAdmin(String userId) throws TException {
		// TODO Auto-generated method stub
		return handler.getUser(userId).isAdmin();
	}

	@Override
	public int logout(String userId) throws TException {
		// TODO Auto-generated method stub
		return handler.logout(userId);
	}

	@Override
	public UserModel findUserInfo(String userName, String userEmail) throws TException {
		// TODO Auto-generated method stub
		return handler.findUser(userName, userEmail);
	}

	@Override
	public UserModel getUserModel(String userId) throws TException {
		// TODO Auto-generated method stub
		
		UserModel userModel = null;
		
		try {
			
			userModel = handler.getUser(userId);
		} catch (TApplicationException e) {
			
			userModel = null;
		}

		return userModel;
	}

	@Override
	public boolean existUserId(String userId) throws TException {
		// TODO Auto-generated method stub

		return handler.userCheck(userId);
	}

	@Override
	public String getTempPasswd(String userId, String userEmail) throws TException {
		// TODO Auto-generated method stub

		return handler.getTempPassword(userId, userEmail);
	}

	@Override
	public int updatePassword(String userId, String userEmail, String passwd) throws TException {
		// TODO Auto-generated method stub

		return handler.updatePassword(userId, userEmail, passwd);
	}

	@Override
	public boolean userCheckWithMail(String userId, String userEmail) throws TException {
		// TODO Auto-generated method stub
		return handler.userCheckWithMail(userId, userEmail);
	}

	public int updateUserInfo(String userId, String userEmail, String password) throws TException{
		// TODO Auto-generated method stub
		return handler.updatePassword(userId, userEmail, password);
	}
}
