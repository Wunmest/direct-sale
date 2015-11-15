package com.wunmest.database.dao;

import java.util.List;

import com.wunmest.database.entity.Role;
import com.wunmest.database.entity.User;

public interface UserDao {

	public int insert(User user);
	
	public int update(User user);
	
	public List<User> selectByRealname(String realname);
	
	public User selectByTel(long tel);
	
	public List<User> selectOriginUsers();
	
	public List<User> selectByReferrer(long uid);
	
	public int insertUserRoles(User user);
	
}
