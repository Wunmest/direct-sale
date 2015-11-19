package com.wunmest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wunmest.database.dao.BankDao;
import com.wunmest.database.dao.RoleDao;
import com.wunmest.database.dao.UserDao;
import com.wunmest.database.entity.Role;
import com.wunmest.database.entity.User;
import com.wunmest.domain.XException;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
public class UserService {

	@Resource
	private UserDao userDao;
	@Resource
	private BankDao bankDao;
	@Resource
	private RoleDao roleDao;
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public void reg(User user, HttpServletRequest request) throws XException{
		Logger log = Logger.getLogger(UserService.class);
		
		boolean isOrigin = Long.valueOf(0) == user.getReferrer().getUid();
		User referrer = null;
		
		/* 推荐人是否满足条件 begin */
		// 首先推荐人得存在
		if(!isOrigin){
			referrer = userDao.selectByTel(user.getReferrer().getTel());
			if(referrer == null){
				throw new XException(0, "推荐人不存在, 注册失败", null);
			}
			user.setReferrer(referrer);
		}
		// 检查推荐人数是否已达上限, 上限: (升级次数 + 1) * 2
		int maxRecommendedUsers = 0;
		maxRecommendedUsers = (userDao.selectPromotionTimes(referrer.getUid()) + 1) * 2;
		List<User> recommendedUsers = userDao.selectByReferrer(referrer.getUid());
		if(recommendedUsers.size() == (isOrigin ? 8 : maxRecommendedUsers))
			throw new XException(0, "该用户推荐人数已达上限, 注册失败", null);
		/* 推荐人是否满足条件 end */
		
		//刚注册时, 推荐人即为上级
		user.setSuperior(referrer);
		
		//用户的角色: 推荐人角色的下级角色
		List<Role> userRoles = new ArrayList<Role>();
		if(isOrigin){
			userRoles.add(roleDao.selectByName("初始代理"));
			userRoles.add(roleDao.selectByName("1级代理"));			
		}else{
			List<Role> referrerRoles = roleDao.selectByUid(user.getReferrer().getUid());
			Pattern rolePattern = Pattern.compile("^(\\d+)级代理$");
			for(Role role : referrerRoles){
				Matcher roleMatcher = rolePattern.matcher(role.getRoleName());
				if(roleMatcher.find()){
					//推荐人的下级中文角色名称
					String roleName = String.valueOf(Integer.parseInt(roleMatcher.group(1)) + 1).concat("级代理");
					//检查角色是否存在, 如果角色不存在, 还需创建该角色
					Role userRole = roleDao.selectByName(roleName);
					if(userRole == null){
						userRole = new Role();
						userRole.setRoleName(roleName);
						if(roleDao.insert(userRole) != 1) 
							throw new XException(0, "注册失败, 请稍后再试", null);
					}
					userRoles.add(userRole);
					break;
				}
			}
		}
		user.setRoles(userRoles);
		
		//银行卡信息
		long ccn = Long.valueOf(request.getParameter("ccn"));
		String bank = request.getParameter("bank");
		
		//保存用户信息
		if(user.getRoles().isEmpty() || 
				userDao.insert(user) < 1 || 
				userDao.insertUserRoles(user) < 1 || 
				bankDao.insert(user.getUid(), ccn, bank) < 1)
			throw new XException(0, "注册失败, 请稍后再试", null);
		
		//注册完以后要检查上级推荐人
		/*
		 * 角色: 上下级关系
		 * 推荐人: 只是记录作用
		 * 
		 */
	}
	
	
	
	
	
	/*
	public List<User> selectByRealname(String realname){
		return userDao.selectByRealname(realname);
	}
	*/
}