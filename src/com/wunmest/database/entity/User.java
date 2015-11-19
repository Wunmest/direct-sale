package com.wunmest.database.entity;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * User 实体类
 * @author 王春最美
 *
 */
@Component
@Scope("prototype")
public class User {

	private Long uid;
	private String username;
	private String password;
	private String realname;
	private Integer gender;
	private long tel;
	private String uuid;
	private String regTime;
	private User referrer;
	private List<Role> roles;
	private User superior;
	private String path; 
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public User getSuperior() {
		return superior;
	}
	public void setSuperior(User superior) {
		this.superior = superior;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public long getTel() {
		return tel;
	}
	public void setTel(long tel) {
		this.tel = tel;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public User getReferrer() {
		return referrer;
	}
	public void setReferrer(User referrer) {
		this.referrer = referrer;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", realname=" + realname + ", gender=" + gender
				+ ", tel=" + tel + ", uuid=" + uuid + ", regTime=" + regTime
				+ ", referrer=" + referrer + ", roles=" + roles + "]";
	}
	
	
}
