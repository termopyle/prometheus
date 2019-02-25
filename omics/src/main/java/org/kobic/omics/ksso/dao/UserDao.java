package org.kobic.omics.ksso.dao;

import org.apache.thrift.TException;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.omics.ksso.vo.UserVo;

public interface UserDao {

	public boolean isAdmin(String userId) throws TException;
	public boolean login(String userId, String passwd) throws TException;
	public String kssoUserRegister(UserVo userVo) throws TException;
	public int logout(String userId) throws TException;
	public UserModel getUserModel(String userId) throws TException;
	public UserModel findUserInfo(String userName, String userEmail) throws TException;
	public boolean existUserId(String userId) throws TException;
	public String getTempPasswd(String userId, String userEmail) throws TException;
	public int updatePassword(String userId, String userEmail, String passwd) throws TException;
	public boolean userCheckWithMail(String userId, String userEmail) throws TException;
	public int updateUserInfo(String userId, String userEmail, String password) throws TException;
}
