package com.wunmest.database.dao;

import org.apache.ibatis.annotations.Param;

public interface BankDao {
	public int insert(
			@Param("uid") long uid, 
			@Param("ccn") long ccn, 
			@Param("bank") String bank);
}
