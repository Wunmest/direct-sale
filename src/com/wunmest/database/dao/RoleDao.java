package com.wunmest.database.dao;

import java.util.List;

import com.wunmest.database.entity.Role;

public interface RoleDao {

	public int insert(Role role);
	
	public List<Role> selectByUid(long uid);
	
	public Role selectByName(String roleName);
}
